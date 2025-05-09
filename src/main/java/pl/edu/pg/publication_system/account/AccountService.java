package pl.edu.pg.publication_system.account;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public List<AccountPublicDetailsDto> getAccountsPublicDetails() {
        return accountRepository.findAll().stream().map(AccountMapper::toAccountPublicDetailsDto).toList();
    }
}
