package com.example.service;
import com.example.entity.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.repository.AccountRepository;
import java.util.Optional;

@Component
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerAccount(String userName, String passWord){
        Account account = new Account(userName, passWord);
        return accountRepository.save(account);//return the saved account with the account Id

    }
    public boolean accountExists(String userName){
         return accountRepository.existsByUserName(userName);
    }
}
