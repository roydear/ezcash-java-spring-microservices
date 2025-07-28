package com.ec.walletservice.service;

import com.ec.walletservice.dto.WalletBalanceResponseDTO;
import com.ec.walletservice.dto.WalletTransferRequestDTO;
import com.ec.walletservice.dto.WalletTransferResponseDTO;
import com.ec.walletservice.exception.InsufficientBalanceException;
import com.ec.walletservice.exception.ReceiverWalletNotFoundException;
import com.ec.walletservice.exception.SenderWalletNotFoundException;
import com.ec.walletservice.exception.WalletNotFoundException;
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

    public String createWalletAccount(String userId) {

        UUID uuid = UUID.fromString(userId);
        if(walletRepository.findByUserId(uuid).isPresent()) {
            throw new WalletNotFoundException("Wallet already exists " + userId);
        }

        Wallet newWallet = new Wallet();
        newWallet.setUserId(uuid);
        Wallet save = walletRepository.save(newWallet);

        return save.getId().toString();
    }

    @Cacheable(value = "wallet", key = "#userId")
    public WalletBalanceResponseDTO getBalance(UUID userId) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found with ID: " + userId));

        return new WalletBalanceResponseDTO(wallet.getUserId(), wallet.getBalance(), wallet.getCurrency());
    }

    @Transactional
    public WalletTransferResponseDTO transfer(WalletTransferRequestDTO request) {
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

        return new WalletTransferResponseDTO(
                request.getReceiverId(),
                request.getAmount(),
                request.getNote());
    }
}
