package com.rishabhsoft.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;

/**
 * It is responsible for handling the security configuration.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PAGE = "/login";
    private static final String SIGNUP_PAGE = "/signup";
    private static final String USER_MANAGEMENT_PAGE = "/userManagement";
    private static final String LOGOUT_PAGE = "/logout";

    private final DataSource dataSource;

    @Autowired
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * It is responsible for encoding the password.
     * @return The PasswordEncoder object.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * It is responsible for configuring the global security.
     * @param auth The AuthenticationManagerBuilder object.
     * @throws Exception If there is an exception.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select email, password, true from user where email=?")
                .passwordEncoder(passwordEncoder());
    }

    /**
     * It is responsible for configuring the security.
     * @param http The HttpSecurity object.
     * @throws Exception If there is an exception.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(LOGIN_PAGE, SIGNUP_PAGE).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(LOGIN_PAGE)
                .defaultSuccessUrl(USER_MANAGEMENT_PAGE, true)
                .permitAll()
                .and()
                .logout()
                .logoutUrl(LOGOUT_PAGE)
                .logoutSuccessUrl(LOGIN_PAGE)
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
                .and()
                .csrf().disable();
    }
}
