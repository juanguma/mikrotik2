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
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/","/admin/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/agregarempleados/**").hasRole("ADMIN")
                .antMatchers("/Empresa/**").hasRole("ADMIN")
                .antMatchers("/Empleados/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/","/movimientos/empleados/{correo}/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/agregarmovimientos/**").hasAnyRole("ADMIN","USER")
                .antMatchers("/editarMovimiento/{id}").hasAnyRole("ADMIN","USER")

                .and().exceptionHandling().accessDeniedPage("/Denegado")
                .and().logout().permitAll();

    }
}
