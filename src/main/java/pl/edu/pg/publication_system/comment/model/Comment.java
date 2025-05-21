package pl.edu.pg.publication_system.comment.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.article.model.Article;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Article article;

    @ManyToOne
    private Account author;

    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Comment(Article article, Account author, String content) {
        this.article = article;
        this.author = author;
        this.content = content;
    }

}
