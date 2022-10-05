package com.gestion.mikrotik.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;


    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT email,passwd,status from user_app where email =?")
                .authoritiesByUsernameQuery("SELECT email, rol_name from user_app where email=?");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/","VerEmpresas/**").hasRole("ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/Empresa/**").hasRole("ADMIN")
                .antMatchers("/Empleado/**").hasRole("ADMIN")
                .antMatchers("/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/AgregarMovimiento/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/EditarMovimiento/**").hasAnyRole("ADMIN","USER")
                .and().formLogin().permitAll()
                .and().exceptionHandling().accessDeniedPage("/denegado")
                .and().logout().permitAll();
    }
}
