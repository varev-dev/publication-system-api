package pl.edu.pg.publication_system.auth;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.edu.pg.publication_system.account.Account;
import pl.edu.pg.publication_system.account.AccountService;
import pl.edu.pg.publication_system.account.SubscriptionLevel;

import java.time.LocalDate;

@AllArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> {
                authorize
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/account/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/article").permitAll()
                    .requestMatchers(HttpMethod.GET, "/article/admin").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/article").hasRole("EDITOR")
                    .anyRequest().authenticated();
            })
            .csrf((csrf) -> {
                csrf.ignoringRequestMatchers("/h2-console/**")
                    .ignoringRequestMatchers("/auth/**");
            })
            .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
            .formLogin(FormLoginConfigurer::disable)
            .httpBasic(Customizer.withDefaults())
            .authenticationProvider(authenticationProvider());

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        Account admin = new Account("admin", passwordEncoder().encode("sa"),
                Role.ADMIN, LocalDate.of(2000, 1, 1), SubscriptionLevel.PREMIUM, true);
        Account user = new Account("user", passwordEncoder().encode("sa"),
                Role.USER, LocalDate.of(2000, 1, 1), SubscriptionLevel.FREE, true);

        if (userDetailsService instanceof AccountService service) {
            service.save(admin);
            service.save(user);
        }
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
