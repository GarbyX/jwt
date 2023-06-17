package com.garby.jwt.exceptions;

public class UnauthorizedException extends Throwable {
    public UnauthorizedException(String invalid_username_or_password) {
    }
}
