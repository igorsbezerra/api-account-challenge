package dev.igor.apiaccount.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Transaction {
    private String id;
    private String sourceAccount;
    private String targetAccount;
    private String amount;
    private String type;
}
