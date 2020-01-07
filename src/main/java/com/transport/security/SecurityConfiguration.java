package com.transport.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/v1/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/v1/cars").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/v1/cars/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/v1/cars/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/v1/cars/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();

    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        "/v2/api-docs", "/configuration/ui", "/swagger-resources/**", "/configuration/security",
                        "/swagger-ui.html", "/webjars/**");
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
                .inMemoryAuthentication().withUser("admin").password(encoder.encode("admin")).roles("ADMIN").and()
                .withUser("driver").password(encoder.encode("driver")).roles("USER", "ADMIN");
    }
}
