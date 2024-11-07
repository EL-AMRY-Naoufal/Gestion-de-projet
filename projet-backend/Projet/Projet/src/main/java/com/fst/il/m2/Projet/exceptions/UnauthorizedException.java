package com.fst.il.m2.Projet.exceptions;


public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        this(null, null);
    }

    public UnauthorizedException(String message) {
        this(message, null);
    }

    public UnauthorizedException(Throwable cause) {
        this(cause != null ? cause.getMessage() : null, cause);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message);
        if (cause != null) super.initCause(cause);
    }
}

