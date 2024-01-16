package dev.igor.apiaccount.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountAvailableBalanceResponse {
    private String available;

    public AccountAvailableBalanceResponse(boolean available) {
        this.available = String.valueOf(available);
    }
}
