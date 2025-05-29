package pl.edu.pg.publication_system.comment.dto;

import java.time.LocalDateTime;

public record CommentDTO(Long id, String author, String content, LocalDateTime createdAt) {
}
