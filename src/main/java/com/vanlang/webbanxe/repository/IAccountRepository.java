package com.vanlang.webbanxe.repository;

import com.vanlang.webbanxe.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByUsername(String username);
}
