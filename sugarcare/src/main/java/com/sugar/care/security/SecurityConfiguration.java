package com.sugar.care.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailService).passwordEncoder(getPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .httpBasic()                      // it indicate basic authentication is requires
                .and()
                .authorizeRequests()
                .antMatchers( HttpMethod.POST,"/user/users").permitAll() // /index will be accessible directly, no need of any authentication
                .anyRequest().authenticated();   // it's indicate all request will be secure
        http.csrf().disable();
        http.formLogin().disable();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        //for demo purpose no password encoder
        return NoOpPasswordEncoder.getInstance();
    }
}
