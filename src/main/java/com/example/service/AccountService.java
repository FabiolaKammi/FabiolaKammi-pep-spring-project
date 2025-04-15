package com.example.service;
import com.example.entity.Account;
import com.example.entity.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.repository.AccountRepository;

import java.util.List;
import java.util.Optional;
import com.example.exception.exception.DuplicateAccountException;
import java.util.stream.Stream;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(Account account) throws DuplicateAccountException {
        if(account.getUsername()== null || account.getUsername().isBlank() ||
        account.getPassword() == null || account.getPassword().length() < 4 || account.getPassword().isBlank()){
            throw new IllegalArgumentException("Invalid username and password ");
        }
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new DuplicateAccountException("Username already exists");
        }
        return accountRepository.save(account);
    }

    public Optional<Account> loginAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() ||
            account.getPassword() == null || account.getPassword().isBlank()) {
            return Optional.empty();
        }
        Optional<Account> foundAccount = accountRepository.findByUsername(account.getUsername());
        if (foundAccount.isPresent() && foundAccount.get().getPassword().equals(account.getPassword())) {
            return foundAccount;
        }
        return Optional.empty();
    }
    
}


