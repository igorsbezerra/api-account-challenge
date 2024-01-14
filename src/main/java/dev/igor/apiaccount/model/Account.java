package dev.igor.apiaccount.model;

import dev.igor.apiaccount.model.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    private String id;
    @Column(name = "account_code", unique = true, length = 5)
    private String accountCode;
    @Column(name = "agency", length = 50)
    private String agency;
    @Column(name = "user_id", length = 36)
    private String userId;
    @Column(name = "account_balance", precision = 10, scale = 2)
    private BigDecimal accountBalance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    public static Account create(String accountCode, String agency, String userId) {
        String id = UUID.randomUUID().toString();
        BigDecimal balance = new BigDecimal(1000);
        return new Account(id, accountCode, agency, userId, balance, AccountStatus.ACTIVE);
    }
}
