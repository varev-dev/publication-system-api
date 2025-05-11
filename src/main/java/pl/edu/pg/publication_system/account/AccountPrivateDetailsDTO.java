package pl.edu.pg.publication_system.account;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AccountPrivateDetailsDTO(String username, boolean verified, String role, String subscriptionLevel,
                                       LocalDate birth, LocalDateTime createdAt) {
}
