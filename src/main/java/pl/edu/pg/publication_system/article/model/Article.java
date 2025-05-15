package pl.edu.pg.publication_system.article.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import pl.edu.pg.publication_system.account.model.Account;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.lang.Integer.max;

@NoArgsConstructor
@Data
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Account author;

    @NotNull
    @Column(unique = true)
    private String title;
    @NotNull
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    private int requiredAge = 0;
    private Duration requiredAccountAge;

    public Article(@NotNull String title, @NotNull String content, int requiredAge, Duration requiredAccountAge) {
        this.title = title;
        this.content = content;
        this.requiredAge = max(requiredAge, 0);
        this.requiredAccountAge = requiredAccountAge;
    }

    public boolean hasRequiredAge() {
        return requiredAge > 0;
    }

    public boolean hasRequiredAccountAge() {
        return requiredAccountAge != null;
    }

}
