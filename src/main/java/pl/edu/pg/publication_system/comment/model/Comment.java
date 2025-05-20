package pl.edu.pg.publication_system.comment.model;

import jakarta.persistence.*;
import lombok.Data;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.article.model.Article;

import java.time.LocalDateTime;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Account author;
    private String content;
    private LocalDateTime createdAt;
}
