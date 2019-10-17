package py.com.global.spm.GUISERVICE.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import py.com.global.spm.GUISERVICE.security.service.CustomWebAuthenticationDetails;
import py.com.global.spm.GUISERVICE.security.service.UserDetailsServiceImpl;
import py.com.global.spm.GUISERVICE.services.UserService;
import py.com.global.spm.GUISERVICE.utils.SPM_GUI_Constants;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

/**
 * @author cdelgado
 * @version 1.0
 * @since 20/10/2017
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LogManager.getLogger(AuthenticationTokenFilter.class);

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private TokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;


    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authToken = request.getHeader(this.tokenHeader);
        logger.debug("TOKEN: {} " ,authToken);
        String email = jwtTokenUtil.getEmailFromToken(authToken);
        logger.debug("verificando autenticación del usuario con email: {}", email);
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User userDetails = (User) this.userDetailsService.loadUserByUsername(email);
            if (jwtTokenUtil.validateTokenFromEmail(authToken, userDetails)) {
                Boolean canChangePassword = jwtTokenUtil.getClaimKeyChangePassword(authToken);
                Collection<? extends GrantedAuthority> authorities = null;

                if(canChangePassword!=null && userDetails.getRecoveryToken()!=null && userDetails.getTimeValidateToken().after(new Date()))
                    authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(SPM_GUI_Constants.ROLE_CHANGE_PASSWORD);
                else if(canChangePassword == null)
                    authorities = userDetails.getAuthorities();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                Long idCompany= jwtTokenUtil.getIdCompanyFromToken(authToken);
                authentication.setDetails(authenticationDetailsSource().buildDetails(request));
                logger.debug("Token válido para el usuario con email: {}", email);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                String token = jwtTokenUtil.generateTokenWithEmail(userDetails,idCompany);
                response.setHeader(tokenHeader,token);
            }else {
                logger.warn("Token inválido para el usuario {} pudo haber expirado o la fecha de cambio" +
                        "de password del usuario esta mal",email);
            }
        }
        chain.doFilter(request, response);
    }
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource() {
        return request -> new CustomWebAuthenticationDetails(request,request.getHeader(tokenHeader));
    }
}
