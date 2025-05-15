package pl.edu.pg.publication_system.article.dto;

import java.time.LocalDateTime;

public record ArticleSummaryDTO(String title, String author, LocalDateTime createdAt) {
}
