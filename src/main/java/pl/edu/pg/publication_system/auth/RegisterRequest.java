package pl.edu.pg.publication_system.auth;

import java.time.LocalDate;

public record RegisterRequest(String username, String password, LocalDate birth) {
}
