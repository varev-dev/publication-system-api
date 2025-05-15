package pl.edu.pg.publication_system.article.dto;

import pl.edu.pg.publication_system.article.model.TimeUnit;

public record ArticleCreationRequest(String title, String content, int requiredAge, int requiredAccountAge, TimeUnit timeUnit) {
}
