package pl.edu.pg.publication_system.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.article.model.Article;

import java.util.List;
import java.util.UUID;

public interface ArticleRepository extends JpaRepository<Article, UUID> {

    List<Article> findAllByAuthor(Account author);

}
