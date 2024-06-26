package com.eventHub.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/eventos/cadastrar").hasRole("ORGANIZER")
                        .requestMatchers("/usuarios/dashboard").authenticated()
                        .requestMatchers("/eventos").permitAll()
                        .requestMatchers("/eventos/pesquisar").permitAll()
                        .requestMatchers("/usuarios/cadastro").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/validar/**").permitAll()
                        .requestMatchers("/recuperarSenha").permitAll()
                        .requestMatchers("/redefinirSenha").permitAll()
                        .requestMatchers("/**").permitAll()
                        .anyRequest().authenticated() // Qualquer outra requisição precisa ser autenticada
                )
                .formLogin(form -> form
                        .loginPage("/login") // Define a página de login personalizada
                        .defaultSuccessUrl("/eventos")
                        .permitAll() // Permite acesso à página de login para todos
                )
                .logout(logout -> logout
                        .permitAll()
                        .logoutSuccessUrl("/eventos")// Permite logout para todos
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // Limita a sessão para um único login por vez
                        .expiredUrl("/login?expired=true") // Redireciona para a página de login se a sessão expirar
                );
        return http.build();
    }
}
