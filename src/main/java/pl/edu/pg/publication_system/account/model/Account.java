package pl.edu.pg.publication_system.account.model;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.pg.publication_system.article.model.Article;
import pl.edu.pg.publication_system.auth.dto.RegisterRequest;
import pl.edu.pg.publication_system.auth.model.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Account implements UserDetails {
    private static final int LEGAL_ADULT_AGE = 18;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;
    private boolean verified;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SubscriptionLevel level;

    private LocalDate birth;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "author_id")
    List<Article> articles;

    public Account(String username, String password, Role role, LocalDate birth, SubscriptionLevel level, boolean verified) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.birth = birth;
        this.level = level;
        this.verified = verified;

        this.createdAt = LocalDateTime.now();
    }

    public Account(String username, String password, Role role, LocalDate birth, SubscriptionLevel level) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.birth = birth;
        this.level = level;

        this.verified = false;
        this.createdAt = LocalDateTime.now();
    }

    public Account(RegisterRequest register, String hashedPassword) {
        this.username = register.username();
        this.password = hashedPassword;
        this.role = Role.USER;
        this.birth = register.birth();

        this.verified = false;
        this.level = SubscriptionLevel.FREE;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    public boolean isAdult() {
        return getUserAge() >= LEGAL_ADULT_AGE;
    }

    public int getUserAge() {
        return Period.between(birth, LocalDate.now()).getYears();
    }

    public long getAccountAgeInHours() {
        return ChronoUnit.HOURS.between(createdAt, LocalDateTime.now());
    }
}
