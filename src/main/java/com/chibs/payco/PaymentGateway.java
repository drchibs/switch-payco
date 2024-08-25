package com.chibs.payco;

import com.chibs.payco.dto.GatewayResponseDto;
import com.chibs.payco.dto.TransactionDto;

import java.util.Map;

public interface PaymentGateway {

    GatewayResponseDto processPayment(TransactionDto transaction);


    Object handleWebhook(String webhookData);
}
