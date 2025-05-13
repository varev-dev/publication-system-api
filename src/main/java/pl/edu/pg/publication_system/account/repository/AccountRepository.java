package pl.edu.pg.publication_system.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pg.publication_system.account.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    boolean existsByUsernameIgnoreCase(String username);
}
