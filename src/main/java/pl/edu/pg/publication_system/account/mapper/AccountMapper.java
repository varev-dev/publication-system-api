package pl.edu.pg.publication_system.account.mapper;

import pl.edu.pg.publication_system.account.dto.AccountPrivateDetailsDTO;
import pl.edu.pg.publication_system.account.dto.AccountPublicDetailsDTO;
import pl.edu.pg.publication_system.account.dto.AccountSummaryDTO;
import pl.edu.pg.publication_system.account.model.Account;

import java.time.LocalDate;
import java.time.Period;

public class AccountMapper {
    public static AccountPrivateDetailsDTO toAccountPrivateDetailsDTO(Account account) {
        return new AccountPrivateDetailsDTO(
                account.getUsername(), account.isVerified(), account.getRole().name(), account.getLevel().name(),
                account.getBirth(), account.getCreatedAt()
        );
    }

    public static AccountPublicDetailsDTO toAccountPublicDetailsDTO(Account account) {
        return new AccountPublicDetailsDTO(
                account.getUsername(), account.isVerified(), account.getRole().name(), account.getLevel().name(),
                account.isAdult(), Period.between(LocalDate.from(account.getCreatedAt()), LocalDate.now())
        );
    }

    public static AccountSummaryDTO toSummaryDTO(Account account) {
        return new AccountSummaryDTO(account.getUsername());
    }
}
