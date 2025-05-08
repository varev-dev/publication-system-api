package pl.edu.pg.publication_system.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> {
                authorize
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/login", "/register").permitAll()
                    .requestMatchers(HttpMethod.GET, "/article").permitAll()
                    .requestMatchers(HttpMethod.POST, "/article").hasRole("EDITOR")
                    .anyRequest().authenticated();
            })
            .csrf((csrf) -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }

}
