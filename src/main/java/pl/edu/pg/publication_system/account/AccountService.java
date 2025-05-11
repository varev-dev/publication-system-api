package pl.edu.pg.publication_system.account;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AccountService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<AccountPublicDetailsDTO> getAccountPublicDetails(String username) {
        return accountRepository.findByUsername(username).map(AccountMapper::toAccountPublicDetailsDTO);
    }

    public List<AccountSummaryDTO> getAccountsSummaries() {
        return accountRepository.findAll().stream().map(AccountMapper::toSummaryDTO).toList();
    }

    public List<AccountPublicDetailsDTO> getAccountsPublicDetails() {
        return accountRepository.findAll().stream().map(AccountMapper::toAccountPublicDetailsDTO).toList();
    }

    public Account updateAccount(String username, AccountUpdateDTO updateDTO) {
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        if (updateDTO.birth() != null)
            account.setBirth(updateDTO.birth());

        return accountRepository.save(account);
    }
}
