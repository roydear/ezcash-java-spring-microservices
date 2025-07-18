package com.ec.walletservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleWalletNotFoundException(
            WalletNotFoundException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Wallet not found");
        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(SenderWalletNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleSenderWalletNotFoundException(
            SenderWalletNotFoundException ex
    ) {

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Sender wallet not found");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ReceiverWalletNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleReceiverWalletNotFoundException(
            ReceiverWalletNotFoundException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Receiver Wallet not Found");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientBalanceException(
            InsufficientBalanceException ex
    ) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Insufficient Balance");
        return ResponseEntity.badRequest().body(errors);
    }
}
