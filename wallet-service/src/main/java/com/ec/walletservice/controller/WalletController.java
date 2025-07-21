package com.ec.walletservice.controller;

import com.ec.walletservice.dto.WalletBalanceResponseDTO;
import com.ec.walletservice.dto.WalletTransferRequestDTO;
import com.ec.walletservice.dto.WalletTransferResponseDTO;
import com.ec.walletservice.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/wallet")
@Tag(name = "Wallet", description = "API for requesting wallet balance and transfer wallet amount to another")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/balance/{userId}")
    @Operation(summary = "Get wallet balance of a user")
    public ResponseEntity<WalletBalanceResponseDTO> getBalance(@PathVariable UUID userId) {
        return ResponseEntity.ok().body(walletService.getBalance(userId));
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer wallet amount to another wallet")
    public ResponseEntity<WalletTransferResponseDTO> transfer(@RequestBody WalletTransferRequestDTO request) {
        WalletTransferResponseDTO transfer = walletService.transfer(request);
        return ResponseEntity.ok().body(transfer);
    }
}
