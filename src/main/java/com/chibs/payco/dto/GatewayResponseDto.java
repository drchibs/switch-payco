package com.chibs.payco.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayResponseDto {
    private String transactionReference;
    private String amount;
    private String currency;
    private String status;
    private String type;
}
