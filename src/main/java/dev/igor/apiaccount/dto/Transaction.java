package dev.igor.apiaccount.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private String sourceAccount;
    private String targetAccount;
    private String amount;
}
