package com.example.controller;

import java.net.http.HttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.Account;
import com.example.entity.Message;




/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping(path = "api/accounts")

@CrossOrigin("*")
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity <Account> registerAccount(@RequestBody Account account){
        String userName = account.getUsername();
        String passWord = account.getPassword();

        // validate input parameters
        if(userName == null ||userName.isBlank() ||passWord ==null ||passWord.isBlank() || passWord.length() < 4){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // check for duplicate username
        if(accountService.accountExists(userName)){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        // Register the account
        Account createdAccount = accountService.registerAccount(userName,passWord);
        if(createdAccount != null){
            return new ResponseEntity<>(createdAccount, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);// failed
        }
        
    }
}
