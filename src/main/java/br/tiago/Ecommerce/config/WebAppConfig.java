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
                                 .requestMatchers("/person/update/**").authenticated()
                                 .anyRequest().authenticated()
                );

        http.formLogin((login) ->
                login.loginPage("/person/login")
                        .defaultSuccessUrl("/home", true)
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
