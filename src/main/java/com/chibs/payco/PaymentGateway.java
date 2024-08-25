package com.chibs.payco;

import com.chibs.payco.dto.TransactionDto;

import java.util.Map;

public interface PaymentGateway {

    Map<String, Object> processPayment(TransactionDto transaction);

    Object handleWebhook(String webhookData);
}
