package pl.edu.pg.publication_system.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.publication_system.comment.model.Comment;

public interface CommentRepository extends JpaRepository<Long, Comment> {
}
