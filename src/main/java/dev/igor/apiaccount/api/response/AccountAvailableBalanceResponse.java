package dev.igor.apiaccount.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountAvailableBalanceResponse {
    private String available;
}
