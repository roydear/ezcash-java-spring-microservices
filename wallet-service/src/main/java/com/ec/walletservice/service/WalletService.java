package com.ec.walletservice.service;

import com.ec.walletservice.dto.WalletBalanceResponse;
import com.ec.walletservice.dto.WalletTransferRequest;
import com.ec.walletservice.dto.WalletTransferResponse;
import com.ec.walletservice.exception.InsufficientBalanceException;
import com.ec.walletservice.exception.ReceiverWalletNotFoundException;
import com.ec.walletservice.exception.SenderWalletNotFoundException;
import com.ec.walletservice.model.Wallet;
import com.ec.walletservice.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Cacheable(value = "wallet", key = "#userId")
    public WalletBalanceResponse getBalance(UUID userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found with ID: " + userId));

        return new WalletBalanceResponse(wallet.getUserId(), wallet.getBalance(), wallet.getCurrency());
    }

    @Transactional
    public WalletTransferResponse transfer(WalletTransferRequest request) {
        Wallet sender = walletRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new SenderWalletNotFoundException("Sender wallet not found with ID: "
                        + request.getUserId()));

        Wallet receiver = walletRepository.findByUserId(request.getReceiverId())
                .orElseThrow(() -> new ReceiverWalletNotFoundException("Receiver wallet not found with ID: "
                        + request.getReceiverId()));

        if (sender.getBalance().compareTo(request.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance().subtract(request.getAmount()));
        receiver.setBalance(receiver.getBalance().add(request.getAmount()));

        walletRepository.saveAll(List.of(sender, receiver));

        return new WalletTransferResponse(
                request.getReceiverId(),
                request.getAmount(),
                request.getNote());
    }
}
