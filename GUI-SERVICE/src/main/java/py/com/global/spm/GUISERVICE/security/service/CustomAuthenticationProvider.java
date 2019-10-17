package py.com.global.spm.GUISERVICE.security.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import py.com.global.spm.GUISERVICE.dto.ErrorsDTO;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsServiceImpl userDetailsService;

    private final BCryptPasswordEncoder encoder;

    public CustomAuthenticationProvider(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication){
        CustomUsernamePasswordAuthenticationToken auth = (CustomUsernamePasswordAuthenticationToken) authentication;
        UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getPrincipal().toString());
        if(!encoder.matches(auth.getUserInputCaptchaValue(),auth.getCaptchaEncode()))
            throw new BadCredentialsException(ErrorsDTO.CODE.INVALIDCAPTCHA.getValue());
        if (StringUtils.isBlank(auth.getCredentials().toString())
                || !encoder.matches(auth.getCredentials().toString(),userDetails.getPassword()) ){
            throw new BadCredentialsException(ErrorsDTO.CODE.BADCREDENTIALS.getValue());
        }
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomUsernamePasswordAuthenticationToken.class);
    }
}
