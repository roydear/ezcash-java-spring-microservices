package com.ec.walletservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletTransferResponse(UUID receiverId, BigDecimal amount, String note) {
}
