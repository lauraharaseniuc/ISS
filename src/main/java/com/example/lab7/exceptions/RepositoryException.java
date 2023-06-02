package com.example.lab7.exceptions;

public class RepositoryException extends RuntimeException {
    public RepositoryException(Exception e) {
        super(e);
    }

    public RepositoryException(String message) {
        super(message);
    }
}

