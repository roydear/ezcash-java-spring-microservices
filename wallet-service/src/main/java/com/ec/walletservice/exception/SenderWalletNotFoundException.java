package com.ec.walletservice.exception;

public class SenderWalletNotFoundException extends RuntimeException {
    public SenderWalletNotFoundException(String message) {
        super(message);
    }
}
