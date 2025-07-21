package com.ec.walletservice.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.UUID;

public class WalletTransferRequestDTO {

    @NotBlank(message = "User Id is required")
    private UUID userId;

    @NotBlank(message = "Receiver Id is required")
    private UUID receiverId;

    @NotBlank(message = "Amount is Required")
    private BigDecimal amount;

    private String note;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(UUID receiverId) {
        this.receiverId = receiverId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
