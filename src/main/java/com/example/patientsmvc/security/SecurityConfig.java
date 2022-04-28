package com.example.patientsmvc.security;

import com.example.patientsmvc.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
@Autowired
private DataSource dataSource;
@Autowired
private UserDetailsServiceImpl userDetailsService;
@Autowired
private PasswordEncoder passwordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


       /* String encodedPWD=passwordEncoder.encode("omar");
        System.out.println(encodedPWD);
        auth.inMemoryAuthentication().withUser("omar").password(encodedPWD).roles("USER");
        auth.inMemoryAuthentication().withUser("omar1").password(passwordEncoder.encode("omar")).roles("USER","ADMIN");*/
/*auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select username as principal,password as credentials,active from users where username=?")
        .usersByUsernameQuery("select username as principal, role as role from users_roles where username=?")
        .rolePrefix("ROLE_")
        .passwordEncoder(passwordEncoder);*/
        auth.userDetailsService(userDetailsService);

    }

    @Override
protected void configure(HttpSecurity http) throws Exception{
  http.formLogin();
        http.authorizeHttpRequests().antMatchers("/").permitAll();
        http.authorizeHttpRequests().antMatchers("/webjars/**").permitAll();
        http.authorizeHttpRequests().antMatchers("/admin/**").hasAuthority("ADMIN");
        http.authorizeHttpRequests().antMatchers("/user/**").hasAuthority("USER");
http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedPage("/403");
    }



}