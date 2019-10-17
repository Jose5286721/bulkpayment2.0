package py.com.global.spm.GUISERVICE.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author cdelgado
 * @version 1.0
 * @since 20/10/17
 */
@Component
public class TokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_EMAIL = "sub";
    private static final String CLAIM_KEY_AUDIENCE = "audience";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_ID_COMPANY = "idCompany";
    private static final String CLAIM_KEY_CHANGE_PASSWORD = "changePassword";
    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private static final Logger logger = LogManager
            .getLogger(TokenUtil.class);


    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String getEmailFromToken(String token) {
        String email;
        try {
            final Claims claims = getClaimsFromToken(token);
            email = claims.getSubject();
        } catch (Exception e) {
            email = null;
        }
        return email;
    }

    public Boolean getClaimKeyChangePassword(String token) {
        Boolean changePassword;
        try {
            final Claims claims = getClaimsFromToken(token);
            changePassword = (boolean)claims.get(CLAIM_KEY_CHANGE_PASSWORD);
        } catch (Exception e) {
            changePassword = null;
        }
        return changePassword;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            logger.warn("Error al obtener fecha de creación", e);
            created = null;
        }
        return created;
    }
    public Date getExpirationDateFromToken(String token) {
        Date expiracion;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiracion = claims.getExpiration();
        } catch (Exception e) {
            logger.warn("Error al obtener fecha de expiración", e);
            expiracion = null;
        }
        return expiracion;
    }
    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            logger.warn("Error al obtener Audience", e);
            audience = null;
        }
        return audience;
    }
    public Long getIdCompanyFromToken(String token){
        Long idCompany;
        try {
            final Claims claims = getClaimsFromToken(token);
            String idCompanyStr = (String) claims.get(CLAIM_KEY_ID_COMPANY);
            idCompany=(idCompanyStr==null || "null".equalsIgnoreCase(idCompanyStr)) ? null:Long.valueOf(idCompanyStr);
        } catch (Exception e) {
            logger.warn("Error al obtener el idCompany", e);
            idCompany = null;
        }
        return idCompany;
    }
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
//            logger.warn("Error al obtener Claims");
            claims = null;
        }
        return claims;
    }


    public boolean isValidToken(String token){
        Claims claims = getClaimsFromToken(token);
        if(claims != null){
            String userName = claims.getSubject();
            return (userName != null && !isTokenExpired(token));

        }
        return false;

    }


    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiracion = getExpirationDateFromToken(token);
        return expiracion.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return lastPasswordReset != null && created.before(lastPasswordReset);
    }
    private String generateAudience(Device device) {
        String audience = AUDIENCE_UNKNOWN;
        if (device.isNormal()) {
            audience = AUDIENCE_WEB;
        } else if (device.isTablet()) {
            audience = AUDIENCE_TABLET;
        } else if (device.isMobile()) {
            audience = AUDIENCE_MOBILE;
        }
        return audience;
    }
    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience);
    }
    public String generateToken(UserDetails userDetails, Device device) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims, generateExpirationDate());
    }
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims,generateExpirationDate());
    }

    /**
     * Metodo para generar el token para el link de recuperacion de contrasenha
     * @param userDetails : Informacion del usuario.
     * @param expiration : Tiempo de validacion en minutos.
     * @return token
     */
    public String generateTokenToRecovery(UserDetails userDetails, Integer expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_CHANGE_PASSWORD,true);
        Date expirationdate = expiration != null ? new Date(System.currentTimeMillis() + expiration * 1000) : generateExpirationDate();
        return generateToken(claims,expirationdate);
    }
    public String generateTokenWithEmail(User userDetails, Device device, Long idCompany) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_EMAIL, userDetails.getUsername());
        claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_ID_COMPANY, String.valueOf(idCompany));
        return generateToken(claims,generateExpirationDate());
    }
    public String generateTokenWithEmail(User userDetails, Long idCompany) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_EMAIL, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put(CLAIM_KEY_ID_COMPANY, String.valueOf(idCompany));
        return generateToken(claims,generateExpirationDate());
    }

    String generateToken(Map<String, Object> claims, Date expirationDate) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims,generateExpirationDate());
        } catch (Exception e) {
            logger.warn("Error al refrescar el token", e);
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        return username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate());
    }

    public boolean validateTokenFromEmail(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        final String email = getEmailFromToken(token);
        final Date created = getCreatedDateFromToken(token);
        return email.equals(user.getUsername())
                && !isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate());
    }


}
