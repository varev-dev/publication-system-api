package pl.edu.pg.publication_system.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.publication_system.article.model.Article;
import pl.edu.pg.publication_system.comment.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticle(Article article);

}
