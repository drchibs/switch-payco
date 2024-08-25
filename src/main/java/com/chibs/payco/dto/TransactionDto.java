package com.chibs.payco.dto;

import com.chibs.payco.core.TransactionType;
import jakarta.annotation.Nonnull;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    private double amount;
    @NonNull
    private TransactionType paymentMethod;
    @NonNull
    private String currency;

    private String transactionReference;
}
