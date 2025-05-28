package pl.edu.pg.publication_system.comment.service;

import org.springframework.stereotype.Service;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.article.service.ArticleService;
import pl.edu.pg.publication_system.comment.model.Comment;
import pl.edu.pg.publication_system.comment.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleService articleService;

    public CommentService(CommentRepository commentRepository, ArticleService articleService) {
        this.commentRepository = commentRepository;
        this.articleService = articleService;
    }

    public List<Comment> findCommentsByArticleId(Long articleId) {
        var articleOpt = articleService.getArticle(articleId);
        return articleOpt.map(commentRepository::findAllByArticle).orElse(null);
    }

    public Comment save(Account account, Long articleId, String content) {
        var articleOpt = articleService.getArticle(articleId);

        if (articleOpt.isEmpty())
            return null;

        Comment comment = new Comment(articleOpt.get(), account, content);
        return commentRepository.save(comment);
    }

    public void removeComment(Long id) {
        var commentOpt = commentRepository.findById(id);
        commentOpt.ifPresent(commentRepository::delete);
    }
}
