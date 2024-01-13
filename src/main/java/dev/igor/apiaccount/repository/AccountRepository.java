package dev.igor.apiaccount.repository;

import dev.igor.apiaccount.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
