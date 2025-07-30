package com.ec.walletservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        err -> err.getField(),
                        err -> err.getDefaultMessage()
                ));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WalletNotFoundException.class)
    public Map<String, String> handleWalletNotFoundException(
            WalletNotFoundException ex
    ) {
        log.warn("Wallet not found {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Wallet not found");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SenderWalletNotFoundException.class)
    public Map<String, String> handleSenderWalletNotFoundException(
            SenderWalletNotFoundException ex
    ) {
        log.warn("Sender wallet not found {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Sender wallet not found");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ReceiverWalletNotFoundException.class)
    public Map<String, String> handleReceiverWalletNotFoundException(
            ReceiverWalletNotFoundException ex
    ) {
        log.warn("Receiver wallet not found {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Receiver Wallet not Found");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InsufficientBalanceException.class)
    public Map<String, String> handleInsufficientBalanceException(
            InsufficientBalanceException ex
    ) {
        log.warn("Insufficient Balance {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        errors.put("message", "Insufficient Balance");
        return errors;
    }
}
