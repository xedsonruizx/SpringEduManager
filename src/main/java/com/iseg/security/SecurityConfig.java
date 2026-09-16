package com.iseg.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(AuthenticationProvider authenticationProvider) {
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))

          .authorizeHttpRequests(auth -> auth
              .requestMatchers("/", "/login", "/registro", "/css/**", "/js/**", "/img/**", "/error").permitAll()
              .requestMatchers("/api/**").permitAll()

              .requestMatchers("/estudiantes/nuevo", "/estudiantes/guardar",
                               "/estudiantes/editar/**", "/estudiantes/eliminar/**").hasRole("ADMIN")

              .requestMatchers("/estudiantes", "/estudiantes/ver/**", "/estudiantes/buscar")
                               .hasAnyRole("ADMIN", "USER")

              .anyRequest().authenticated())

          .formLogin(form -> form
              .loginPage("/login")
              .loginProcessingUrl("/login")
              .defaultSuccessUrl("/", false)
              .permitAll())

          .logout(logout -> logout.logoutSuccessUrl("/").permitAll());

        return http.build();
    }
}
