package py.com.global.spm.GUISERVICE.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author cdelgado
 * @version 1.0
 * @since 20/10/17
 */
public class User implements UserDetails {

    private final Long id;

    private final String username;

    private final String nombre;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private final boolean enabled;

    private final Date lastPasswordResetDate;

    private final String recoveryToken;

    private final Date timeValidateToken;



    public User(Long id, String username, String nombre, String password,
                Collection<? extends GrantedAuthority> authorities, boolean enabled, Date lastPasswordResetDate, String recoveryToken, Date timeValidateToken) {
        this.id = id;
        this.username = username;
        this.nombre = nombre;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.recoveryToken = recoveryToken;
        this.timeValidateToken = timeValidateToken;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    @JsonIgnore
    public String getRecoveryToken() {
        return recoveryToken;
    }

    @JsonIgnore
    public Date getTimeValidateToken() {
        return timeValidateToken;
    }
}
