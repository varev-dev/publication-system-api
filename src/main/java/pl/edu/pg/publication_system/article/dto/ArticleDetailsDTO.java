package pl.edu.pg.publication_system.article.dto;

import pl.edu.pg.publication_system.comment.dto.CommentDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleDetailsDTO(String title, String content, String author, LocalDateTime createdAt, List<CommentDTO> comments) {
}
