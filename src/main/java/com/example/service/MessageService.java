package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.Message;
import com.example.repository.*;


@Component
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message){
        
        if(message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255){
            throw new IllegalArgumentException("Message text can not be null");
        }
        return messageRepository.save(message);
    }
    public List <Message> getAllMessages(){
        return messageRepository.findAll();
    }
    //public Message findById (int messageId){
        //return messageRepository.findById(messageId)
           // .orElseThrow(() -> new EntityNotFoundException("message not found for thid ID"));
    //}
    public Optional <Message> findById(int messageId){
        return messageRepository.findById(messageId);
    }
    public void deleteById (int messageId){
        if(messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
        }
        
    }
    public Optional <Message> updateMessageById(int messageId, String newMessageText ){
        if(newMessageText == null || newMessageText.isBlank()|| newMessageText.length() >255){
            throw new IllegalArgumentException("Invalid message text");
        }
        return messageRepository.findById(messageId)
            .map(message -> {
                message.setMessageText(newMessageText);
                return messageRepository.save(message);
            });
        //Optional<Message> optionalMessage = messageRepository.findById(messageId);
        //if(optionalMessage.isPresent()){
          //  Message messageToUpdate = optionalMessage.get();
            //messageToUpdate.setMessageText(messageText);
            // Message updatedmessage = messageRepository.save(messageToUpdate);
           // return Optional.of(updatedmessage);
        //}
       // return Optional.empty();
        
    }
}
