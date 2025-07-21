package com.ec.walletservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletBalanceResponseDTO(UUID userId,
                                       BigDecimal balance,
                                       String currency) {
}
