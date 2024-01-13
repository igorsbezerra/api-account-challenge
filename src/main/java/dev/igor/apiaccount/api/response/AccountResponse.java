package dev.igor.apiaccount.api.response;

import dev.igor.apiaccount.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountResponse {
    private String id;
    private String accountCode;
    private String userId;

    public static AccountResponse of(Account account) {
        return new AccountResponse(account.getId(), account.getAccountCode(), account.getUserId());
    }
}
