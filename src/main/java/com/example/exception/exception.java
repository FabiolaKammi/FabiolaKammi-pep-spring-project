package com.example.exception;

public class exception {
    public static class DuplicateAccountException extends RuntimeException {
        public DuplicateAccountException(String message){
            super(message);
        }
    }
    public static class DuplicateMessageException extends RuntimeException {
        public DuplicateMessageException(String message){
            super(message);
        }
    }
    
}
