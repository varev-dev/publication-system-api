package pl.edu.pg.publication_system.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.account.mapper.AccountMapper;
import pl.edu.pg.publication_system.account.repository.AccountRepository;
import pl.edu.pg.publication_system.auth.dto.RegisterRequest;

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
        if (accountRepository.existsByUsernameIgnoreCase(request.username())) {
            return ResponseEntity.badRequest().body("Username already in use");
        }

        Account account = new Account(request, passwordEncoder.encode(request.password()));

        try {
            accountRepository.save(account);
        }  catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/check")
    public ResponseEntity<?> check(Authentication auth) {
        Account account = (Account) auth.getPrincipal();
        return ResponseEntity.ok().body(AccountMapper.toAccountPrivateDetailsDTO(account));
    }

}
