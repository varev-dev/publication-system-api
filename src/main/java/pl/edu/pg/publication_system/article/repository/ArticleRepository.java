package pl.edu.pg.publication_system.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.article.model.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {

    List<Article> findAllByAuthor(Account author);

}
