package com.colptha.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Colptha on 3/31/17.
 */
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableWebSecurity
@Configuration
public class SecConfig extends WebSecurityConfigurerAdapter {
    private AuthenticationProvider authenticationProvider;

    @Autowired
    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
                                                               UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable().and()
                .csrf().disable()

                .authorizeRequests().antMatchers("/", "/index","/login","/login?logout").permitAll()
                .and().authorizeRequests().antMatchers("/css/**").permitAll()
                .and().authorizeRequests().antMatchers("/webjars/**").permitAll()
                .and().authorizeRequests().antMatchers("/employee/**").hasAnyRole("ADMIN", "MANAGER")
                .and().authorizeRequests().antMatchers("/product/**").hasAnyRole("ADMIN", "MANAGER")
                .and().authorizeRequests().antMatchers("/shipment/**").hasAnyRole("ADMIN", "MANAGER", "USER")

                .and().authorizeRequests().antMatchers("/h2-console", "/h2-console/**").permitAll()
                .and().authorizeRequests().antMatchers("/account/**").authenticated()


                .and().formLogin()
                    .loginPage("/login")

                .and().exceptionHandling().accessDeniedPage("/access_denied")
                .and().authorizeRequests().anyRequest().authenticated();

    }
}
