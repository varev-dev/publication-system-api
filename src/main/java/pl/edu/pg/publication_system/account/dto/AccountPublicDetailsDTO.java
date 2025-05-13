package pl.edu.pg.publication_system.account.dto;

import java.time.Period;

public record AccountPublicDetailsDTO(String username, boolean verified, String role,
                                      String subscriptionLevel, boolean adult, Period membership) {
}
