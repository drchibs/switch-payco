package com.chibs.payco.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    private double amount;
    private String paymentMethod;
    private String currency;
    private String transactionReference;
}
