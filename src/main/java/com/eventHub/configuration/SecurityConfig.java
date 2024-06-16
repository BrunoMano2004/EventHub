package com.eventHub.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/eventos/cadastrar").hasRole("ORGANIZER") // Permite acesso a qualquer URL que comece com /public sem autenticação
                        .requestMatchers("/eventos").permitAll()
                        .requestMatchers("/eventos/pesquisar").permitAll()
                        .requestMatchers("/usuarios/cadastro").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // Qualquer outra requisição precisa ser autenticada
                )
                .formLogin(form -> form
                        .loginPage("/login") // Define a página de login personalizada
                        .defaultSuccessUrl("/eventos")
                        .permitAll() // Permite acesso à página de login para todos
                )
                .logout(LogoutConfigurer::permitAll // Permite logout para todos
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // Limita a sessão para um único login por vez
                        .expiredUrl("/login?expired=true") // Redireciona para a página de login se a sessão expirar
                );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user") // Define o nome de usuário
                .password(passwordEncoder().encode("senha")) // Define a senha do usuário, codificada usando BCrypt
                .roles("USER") // Define o papel do usuário
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("senha"))
                .roles("ADMIN")
                .build();

        UserDetails organizer = User.builder()
                .username("organizer")
                .password(passwordEncoder().encode("senha"))
                .roles("ORGANIZER")
                .build();
        return new InMemoryUserDetailsManager(user, admin, organizer); // Usa um gerenciador de usuários em memória
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Define o codificador de senha usando BCrypt
    }
}
