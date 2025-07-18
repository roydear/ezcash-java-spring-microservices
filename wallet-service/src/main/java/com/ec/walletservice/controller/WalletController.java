package com.ec.walletservice.controller;

import com.ec.walletservice.dto.WalletBalanceResponse;
import com.ec.walletservice.dto.WalletTransferRequest;
import com.ec.walletservice.dto.WalletTransferResponse;
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
    public ResponseEntity<WalletBalanceResponse> getBalance(@PathVariable UUID userId) {
        return ResponseEntity.ok().body(walletService.getBalance(userId));
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer wallet amount to another wallet")
    public ResponseEntity<WalletTransferResponse> transfer(@RequestBody WalletTransferRequest request) {
        WalletTransferResponse transfer = walletService.transfer(request);
        return ResponseEntity.ok().body(transfer);
    }
}
