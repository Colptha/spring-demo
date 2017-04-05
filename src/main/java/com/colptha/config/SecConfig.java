package com.colptha.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Created by Colptha on 3/31/17.
 */
@Configuration
public class SecConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and().authorizeRequests()
                .antMatchers("/static/**", "/webjars/**", "/", "/login").permitAll()
                .and().authorizeRequests().anyRequest().permitAll();


//                .and().authorizeRequests().antMatchers("/product/all", "product/show/*").hasRole("EMPLOYEE")
//                .and().authorizeRequests().antMatchers("/product/manage/**").hasRole("MANAGER")
//                .and().authorizeRequests().antMatchers("/employee/**").hasRole("ADMIN")
//                .and().authorizeRequests().anyRequest().authenticated();
    }
}
