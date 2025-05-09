package pl.edu.pg.publication_system.account;

public class AccountMapper {
    public static AccountPublicDetailsDto toAccountPublicDetailsDto(Account account) {
        return new AccountPublicDetailsDto(
                account.getUsername(), account.isVerified(), account.getRole().name(),
                account.getLevel().name(), account.isAdult(), account.getCreatedAt()
        );
    }

    public static AccountMinimalDto toAccountMinimalDto(Account account) {
        return new AccountMinimalDto(account.getUsername());
    }
}
