package com.gym.config;

import com.gym.service.UsuarioDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(UsuarioDetailsService uds, PasswordEncoder pe) {
        var p = new DaoAuthenticationProvider();
        p.setUserDetailsService(uds);
        p.setPasswordEncoder(pe);
        return p;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, DaoAuthenticationProvider provider) throws Exception {
        http.authenticationProvider(provider);

        http.authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/", "/inscribete", "/nosotros", "/faq", "/contacto",
                "/registro", "/confirmar", "/horarios", "/login",
               
                "/webjars/**", "/css/**", "/static/**", "/images/**", "/img/**", "/js/**"
            ).permitAll()

        
            .requestMatchers(HttpMethod.GET, "/membresias").permitAll()
            .requestMatchers("/membresias/seleccionar/**", "/membresias/aplicar").authenticated()

           
            .requestMatchers(HttpMethod.GET, "/forgot-password", "/forgot-password/done").permitAll()
            .requestMatchers(HttpMethod.POST, "/forgot-password").permitAll()

          
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/personal/**").hasAnyRole("ADMIN", "PERSONAL")
            .requestMatchers("/perfil/**").authenticated()

            .anyRequest().authenticated()
        );

        http.formLogin(login -> login
            .loginPage("/login").permitAll()
            .loginProcessingUrl("/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .failureUrl("/login?error")
            .defaultSuccessUrl("/perfil", true)
        );

        http.logout(l -> l
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .permitAll()
        );

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
