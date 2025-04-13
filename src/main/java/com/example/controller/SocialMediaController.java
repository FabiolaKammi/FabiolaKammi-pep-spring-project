package com.example.controller;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<?> registerAccount(@RequestBody Account account) {
        try {
            validateAccountInput(account); // Helper method for validation
            //System.out.println(account.getUsername());
            Account createdAccount = accountService.registerAccount(account);
            return ResponseEntity.ok(createdAccount); // 200 OK with the created account
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid username or password"); // 400 Bad Request
        } catch (DuplicateAccountException e) {
            return ResponseEntity.status(409).body(e.getMessage()); // 409 Conflict
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginAccount(@RequestBody Account account){
        
            Optional<Account> loggedAccount = accountService.loginAccount(account);
             return loggedAccount
             .<ResponseEntity<Object>>map(ResponseEntity::ok)
             .orElse(ResponseEntity.status(401).body("Invalid username or password"));
    }

            //if(loggedAccount.isPresent()){
            //return ResponseEntity.ok(loggedAccount.get());//
            //}
            //return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        //} catch (IllegalArgumentException e){
          //  return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        //}
    


    private void validateAccountInput(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank() ||
            account.getPassword() == null || account.getPassword().length() < 4 || account.getPassword().isBlank()){
            throw new IllegalArgumentException("Invalid username or password");
           
        }
    }

    private void validateMessageInput(Message message){
        if(message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255){
            throw new IllegalArgumentException("message should not be null or greather than 255 charaters");
        }
        
    }

    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        try {
            validateMessageInput(message); // method for input validation
            Message createdMessage = messageService.createMessage(message);
            return ResponseEntity.ok(createdMessage); // 200 OK with the created account
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request
        } 
       
    }
   

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages (){
        return ResponseEntity.ok(messageService.getAllMessages());
    }
    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findById (@PathVariable("id") int id){
        return messageService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
        //Message optionalMessage = messageService.findById(messageId);
        //if(optionalMessage != null){
          //  return ResponseEntity.ok(optionalMessage);
        //} else{
          //  return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        //}
    //}
    
    @DeleteMapping("/message/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ("id") int id){
        
            messageService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateMessageById(@PathVariable("id") int id, @RequestBody String newMessageText){
        try{
            validateMessageInput(new Message(null, null, newMessageText,null));
            Optional<Message> updatedMessage = messageService.updateMessageById(id, newMessageText);
            return updatedMessage
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }
}

