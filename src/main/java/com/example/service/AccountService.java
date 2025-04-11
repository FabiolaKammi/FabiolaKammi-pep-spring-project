package com.example.service;
import com.example.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.repository.AccountRepository;
import java.util.Optional;
import com.example.exception.exception.DuplicateAccountException;

@Component
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) throws DuplicateAccountException {
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new DuplicateAccountException("Username already exists");
        }
        return accountRepository.save(account);
    }
}

