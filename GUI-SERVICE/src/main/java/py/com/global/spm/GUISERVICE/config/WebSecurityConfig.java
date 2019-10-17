package py.com.global.spm.GUISERVICE.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import py.com.global.spm.GUISERVICE.security.AuthenticationTokenFilter;
import py.com.global.spm.GUISERVICE.security.service.CustomAuthenticationProvider;
import py.com.global.spm.GUISERVICE.security.service.UserDetailsServiceImpl;


/**
 * @author cdelgado
 * @version 1.0
 * @since 20/10/2017
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new AuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // we don't need CSRF because our token is invulnerable
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // allow anonymous resource requests
                .antMatchers(
                HttpMethod.GET,
                "/",
                "/v2/api-docs",           // swagger
                "/webjars/**",            // swagger-ui webjars
                "/swagger-resources/**",  // swagger-ui resources
                "/configuration/**",      // swagger configuration
                "/*.html",
                "/favicon.ico",
                "/**/*.jpg",
                "/**/*.png",
                "/**/*.html",
                "/**/*.css",
                "/**/*.json",
                "/**/*.svg",
                "/**/*.js",
                "/**/*.woff2",  //font awesome
                "/**/*.woff",   //font awesome
                "/**/*.ttf",    //font awesome
                "/**/*.eot"     //font awesome
                ).permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/forgot/**").permitAll()
                .antMatchers("/validateToken/**").permitAll()
                .antMatchers("/captcha/**").permitAll()

                .anyRequest().authenticated();

//         Custom JWT based security filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
//        // disable page caching
        httpSecurity.headers().cacheControl();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
