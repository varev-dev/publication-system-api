package pl.edu.pg.publication_system.comment.service;

import org.springframework.stereotype.Service;
import pl.edu.pg.publication_system.comment.repository.CommentRepository;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

}
