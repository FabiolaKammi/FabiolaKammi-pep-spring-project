package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.*;



@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public Message createMessage(Message message){
        
        if(message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255){
            throw new IllegalArgumentException("Message text can not be null");
        }
        if(message.getPostedBy() == null || message.getPostedBy().equals(0)){
            throw new IllegalArgumentException("account Id can not be null");
        }
        if (!accountRepository.existsById(message.getPostedBy())) {
            throw new IllegalArgumentException("User does not exist in the database");
        }
        return messageRepository.save(message);
    }
    public List <Message> getAllMessages(){
        return messageRepository.findAll();
    }
    
    public Optional <Message> findById(int messageId){
        return messageRepository.findById(messageId);
    }

    public int deleteById (int messageId){
        if(messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return 1;
        }
        return 0;
        
    }
    

    public Optional<Message> updateMessage(Integer messageId, String newMessageText) {
        // Validate the input messageText
        if (newMessageText == null || newMessageText.isBlank() || newMessageText.length() > 255) {
            throw new IllegalArgumentException("Invalid message text");
        }
    
        // Find the existing message by ID
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
    
        // If the message exists, update the text and save it
        if (optionalMessage.isPresent()) {
            Message messageToUpdate = optionalMessage.get();
            messageToUpdate.setMessageText(newMessageText);
            messageRepository.save(messageToUpdate);
            return Optional.of(messageToUpdate);
        }
    
        // If the message does not exist, return an empty Optional
        return Optional.empty();
    }
    

     // Get message by ID
   public Message getMessageById(int messageId) {
    Optional<Message> message = messageRepository.findById(messageId);
    if(message.isPresent()){
        return message.get();
    } else{
        return null;
    }
}

  // Get All Messages by a Specific User
  public List<Message> getAllMessagesById(int accountId) {
    return messageRepository.findByPostedBy(accountId); 
} 

    
}
