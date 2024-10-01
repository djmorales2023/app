package com.espe.msvc.usuarios.oauth;

import org.bouncycastle.crypto.generators.BCrypt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((oauthHttp) -> oauthHttp
                        .requestMatchers(HttpMethod.GET,"/authorized","/login").permitAll()
                        .requestMatchers(HttpMethod.GET,"/").hasAnyAuthority("SCOPE_read","SCOPE_write")
                        .requestMatchers(HttpMethod.POST,"/").hasAuthority("SCOPE_write")
                        .requestMatchers(HttpMethod.PUT,"/{id}").hasAuthority("SCOPE_write")
                        .requestMatchers(HttpMethod.DELETE,"/{id}").hasAuthority("SCOPE_write")
                        .anyRequest().authenticated())
                .oauth2Login((login)-> login.loginPage("/oauth2/authorization/msvc-usuarios"))
                .oauth2Client(Customizer.withDefaults())
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()));
        return http.build();
    }
}
