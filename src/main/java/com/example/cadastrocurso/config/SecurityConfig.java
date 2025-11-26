package com.example.cadastrocurso.config;

import com.example.cadastrocurso.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

  @Bean
  public UserDetailsService userDetailsService(CustomUserDetailsService impl) {
    return impl;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf()
          .ignoringAntMatchers("/h2-console/**")
          .and()
        .headers()
          .frameOptions().sameOrigin()
          .and()
        .authorizeRequests()
          .antMatchers("/", "/login", "/register", "/h2-console/**", "/css/**", "/js/**").permitAll()
          .antMatchers(HttpMethod.GET, "/api/resources/**").permitAll()
          .antMatchers("/admin").hasRole("ADMIN")
          .anyRequest().authenticated()
          .and()
        .formLogin()
          .loginPage("/login")
          .loginProcessingUrl("/login")
          .defaultSuccessUrl("/dashboard", true)
          .failureUrl("/login?error=true")
          .permitAll()
          .and()
        .logout()
          .logoutUrl("/logout")
          .logoutSuccessUrl("/")
          .permitAll();

    return http.build();
  }
}
