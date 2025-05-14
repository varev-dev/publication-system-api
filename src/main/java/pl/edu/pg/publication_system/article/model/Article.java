package pl.edu.pg.publication_system.article.model;


import jakarta.persistence.*;
import lombok.Data;
import pl.edu.pg.publication_system.account.model.Account;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Account author;

    private String title;
    private String content;

    private LocalDateTime createdAt;

    private int requiredAge;
    private Duration requiredAccountAge;

    public boolean hasRequiredAge() {
        return requiredAge != 0;
    }

    public boolean hasRequiredAccountAge() {
        return requiredAccountAge != null;
    }

}
