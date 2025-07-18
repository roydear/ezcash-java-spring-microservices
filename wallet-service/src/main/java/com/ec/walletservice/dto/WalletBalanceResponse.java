package com.ec.walletservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletBalanceResponse(UUID userId,
                                    BigDecimal balance,
                                    String currency) {
}
