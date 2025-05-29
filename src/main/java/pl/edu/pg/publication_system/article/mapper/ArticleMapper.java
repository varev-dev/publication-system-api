package pl.edu.pg.publication_system.article.mapper;

import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.article.dto.ArticleCreationRequest;
import pl.edu.pg.publication_system.article.dto.ArticleDetailsDTO;
import pl.edu.pg.publication_system.article.dto.ArticleSummaryDTO;
import pl.edu.pg.publication_system.article.model.Article;
import pl.edu.pg.publication_system.article.model.TimeUnit;
import pl.edu.pg.publication_system.comment.dto.CommentDTO;
import pl.edu.pg.publication_system.comment.model.Comment;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ArticleMapper {

    public static Article fromArticleCreationRequest(ArticleCreationRequest request, Account editor){
        Duration duration = durationMapper(request.requiredAccountAge(), request.timeUnit());

        return new Article(editor, request.title(), request.content(), request.requiredAge(), duration);
    }

    public static ArticleSummaryDTO toArticleSummaryDTO(Article article) {
        return new ArticleSummaryDTO(article.getId().toString(), article.getTitle(), article.getAuthor().getUsername(), article.getCreatedAt());
    }

    public static ArticleDetailsDTO toArticleDetailsDTO(Article article) {
        List<Comment> comments = article.getComments();

        if (comments == null) {
            comments = new ArrayList<>();
        }


        return new ArticleDetailsDTO(article.getTitle(), article.getContent(), article.getAuthor().getUsername(),
                article.getCreatedAt(), comments.stream().map(comm ->
                        new CommentDTO(comm.getId(), comm.getAuthor().getUsername(), comm.getContent(), comm.getCreatedAt())).toList());
    }

    public static Duration durationMapper(int amount, TimeUnit unit) {
        return switch (unit) {
            case NULL -> Duration.ZERO;
            case HOUR -> Duration.ofHours(amount);
            case DAY -> Duration.ofDays(amount);
            case WEEK -> Duration.ofDays(7L * amount);
            case MONTH -> Duration.ofDays(31L * amount);
            case YEAR -> Duration.ofDays(365L * amount);
        };
    }

}
