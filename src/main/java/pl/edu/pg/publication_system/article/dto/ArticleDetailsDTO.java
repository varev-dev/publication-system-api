package pl.edu.pg.publication_system.article.dto;

import java.time.LocalDateTime;

public record ArticleDetailsDTO(String title, String content, String author, LocalDateTime createdAt) {
}
