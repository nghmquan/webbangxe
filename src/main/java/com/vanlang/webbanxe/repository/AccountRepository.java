package com.vanlang.webbanxe.repository;

import com.vanlang.webbanxe.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}
