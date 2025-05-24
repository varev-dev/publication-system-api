package pl.edu.pg.publication_system.article.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.comment.model.Comment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "article")
    private List<Comment> comments;

    @NotNull
    @Column(unique = true)
    private String title;
    @NotNull
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();

    private int requiredAge = 0;
    private Duration requiredAccountAge;

    public Article(@NotNull Account author, @NotNull String title, @NotNull String content, int requiredAge, Duration requiredAccountAge) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.requiredAge = max(requiredAge, 0);
        this.requiredAccountAge = requiredAccountAge;
    }

    public boolean hasRequiredAge() {
        return requiredAge > 0;
    }

    public boolean hasRequiredAccountAge() {
        return !requiredAccountAge.equals(Duration.ZERO);
    }

}
