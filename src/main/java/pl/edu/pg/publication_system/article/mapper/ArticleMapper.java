package pl.edu.pg.publication_system.article.mapper;

import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.article.dto.ArticleCreationRequest;
import pl.edu.pg.publication_system.article.dto.ArticleDetailsDTO;
import pl.edu.pg.publication_system.article.dto.ArticleSummaryDTO;
import pl.edu.pg.publication_system.article.model.Article;
import pl.edu.pg.publication_system.article.model.TimeUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ArticleMapper {

    public static Article fromArticleCreationRequest(ArticleCreationRequest request, Account editor){
        Duration duration = durationMapper(request.requiredAccountAge(), request.timeUnit());

        return new Article(editor, request.title(), request.content(), request.requiredAge(), duration);
    }

    public static ArticleSummaryDTO toArticleSummaryDTO(Article article) {
        return new ArticleSummaryDTO(article.getId().toString(), article.getTitle(), article.getAuthor().getUsername(), article.getCreatedAt());
    }

    public static ArticleDetailsDTO toArticleDetailsDTO(Article article) {
        return new ArticleDetailsDTO(article.getTitle(), article.getContent(), article.getAuthor().getUsername(), article.getCreatedAt());
    }

    private static Duration durationMapper(int amount, TimeUnit unit) {
        return switch (unit) {
            case NULL -> Duration.ZERO;
            case HOUR -> Duration.ofHours(amount);
            case DAY -> Duration.ofDays(amount);
            case WEEK -> Duration.of(amount, ChronoUnit.WEEKS);
            case MONTH -> Duration.of(amount, ChronoUnit.MONTHS);
            case YEAR -> Duration.of(amount, ChronoUnit.YEARS);
        };
    }

}
