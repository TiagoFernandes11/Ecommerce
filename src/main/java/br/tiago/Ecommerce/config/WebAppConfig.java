package br.tiago.Ecommerce.config;

import jakarta.websocket.server.PathParam;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebAppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((request) ->
                         request.requestMatchers("/","/home").permitAll()
                                 .requestMatchers("/person/register").permitAll()
                                 .requestMatchers("/person/login").permitAll()
                                 .requestMatchers("/product/view-item/**").permitAll()
                                 .requestMatchers("/assets/**").permitAll()
                                 .requestMatchers("/cart/add").hasRole("USER")
                                 .requestMatchers("/cart/remove").hasRole("USER")
                                 .requestMatchers("/cart").hasRole("USER")
                                 .requestMatchers("/person/update/**").hasRole("USER")
                                 .requestMatchers("/person/profile").hasRole("USER")
                                 .requestMatchers("/product/register").hasRole("ADMIN")
                                 .anyRequest().authenticated()
                );

        http.formLogin((login) ->
                login.loginPage("/person/login")
                        .defaultSuccessUrl("/home")
                        .failureUrl("/person/login?error=true")
        );

        http.headers(httpSecurityHeadersConfigurer ->
                httpSecurityHeadersConfigurer
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
