package com.example.account_service.repository;

import com.example.account_service.entities.Account;
import com.example.account_service.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByUserId(String userId);

    boolean existsByUserIdAndAccountType(String userId, AccountType accountType);

    List<Account> findByUserId(String userId);
}
