package pl.edu.pg.publication_system.account.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.edu.pg.publication_system.account.dto.AccountPrivateDetailsDTO;
import pl.edu.pg.publication_system.account.dto.AccountPublicDetailsDTO;
import pl.edu.pg.publication_system.account.dto.AccountSummaryDTO;
import pl.edu.pg.publication_system.account.dto.AccountUpdateDTO;
import pl.edu.pg.publication_system.account.mapper.AccountMapper;
import pl.edu.pg.publication_system.account.model.Account;
import pl.edu.pg.publication_system.account.service.AccountService;

import java.util.List;
import java.util.Optional;

@RequestMapping(path = "/accounts")
@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/{username}")
    public ResponseEntity<AccountPublicDetailsDTO> getAccountDetails(@PathVariable String username) {
        Optional<AccountPublicDetailsDTO> details = accountService.getAccountPublicDetails(username);

        return details.map(accountPublicDetailsDTO -> ResponseEntity.ok().body(accountPublicDetailsDTO))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<AccountSummaryDTO> getAccountsSummaries() {
        return accountService.getAccountsSummaries();
    }

    @GetMapping(path = "/details")
    public List<AccountPublicDetailsDTO> getAccountsPublicDetails() {
        return accountService.getAccountsPublicDetails();
    }

    @PreAuthorize("#username == authentication.name or hasRole('ADMIN')")
    @PatchMapping(path = "/{username}")
    public ResponseEntity<AccountPrivateDetailsDTO> updateAccount(
            @PathVariable String username,
            @RequestBody AccountUpdateDTO updateDTO) {

        try {
            Account account = accountService.updateAccount(username, updateDTO);
            return ResponseEntity.ok().body(AccountMapper.toAccountPrivateDetailsDTO(account));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
