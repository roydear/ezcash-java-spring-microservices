package com.ec.walletservice.exception;

public class ReceiverWalletNotFoundException extends RuntimeException {
    public ReceiverWalletNotFoundException(String message) {
        super(message);
    }
}
