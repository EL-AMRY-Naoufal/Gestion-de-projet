package com.fst.il.m2.Projet.exceptions;


public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        this(null, null);
    }

    public NotFoundException(String message) {
        this(message, null);
    }

    public NotFoundException(Throwable cause) {
        this(cause != null ? cause.getMessage() : null, cause);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message);
        if (cause != null) super.initCause(cause);
    }
}

