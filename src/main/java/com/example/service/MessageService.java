package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.entity.Message;
import com.example.repository.*;
import com.example.exception.exception.DuplicateMessageException;

@Component

public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message){
        
        if(message.getMessageText().isBlank() || message.getMessageText().length() > 255){
            return null;
        }
        return messageRepository.save(message);
    }
}
