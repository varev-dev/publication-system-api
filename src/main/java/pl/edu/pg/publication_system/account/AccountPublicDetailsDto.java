package pl.edu.pg.publication_system.account;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AccountPublicDetailsDto(String username, boolean verified, String role,
                                      String subscriptionLevel, boolean adult, LocalDateTime createdAt) {
}
