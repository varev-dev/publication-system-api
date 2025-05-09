package pl.edu.pg.publication_system.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.publication_system.account.Account;
import pl.edu.pg.publication_system.account.AccountRepository;

@RequestMapping(path = "/auth")
@RestController
public class AuthController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (accountRepository.existsByUsername(request.username())) {
            return ResponseEntity.badRequest().body("Username already in use");
        }

        Account account = new Account(request, passwordEncoder.encode(request.password()));
        accountRepository.save(account);

        return ResponseEntity.ok().build();
    }

}
