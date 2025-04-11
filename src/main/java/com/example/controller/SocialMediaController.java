package com.example.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.AccountService;
import com.example.service.MessageService;
import com.example.entity.*;
import com.example.exception.exception.*;




/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping(path = "/api")
@CrossOrigin("*")
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        try {
            validateAccountInput(account); // Helper method for validation
            Account createdAccount = accountService.registerAccount(account);
            return ResponseEntity.ok(createdAccount); // 200 OK with the created account
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        } catch (DuplicateAccountException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
        }
    }
    private void validateAccountInput(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() ||
            account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Invalid username or password");
        }
    }

    private void validateMessageInput(Message message){
        if(message.getMessageText().isBlank() ||message.getMessageText() == null || message.getMessageText().length() > 255){
            throw new IllegalArgumentException("message should not be null or greather tha 255 charaters");
        }
        
    }

    @PostMapping("/add")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            validateMessageInput(message); // Helper method for validation
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.ok(createdMessage); // 200 OK with the created account
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request
        } 
       
    }
}

