package com.chibs.payco;

import com.chibs.payco.dto.TransactionDto;

import java.util.Map;

public interface PaymentGateway {

    Map<String, Object> processPayment(String transaction);


    Object handleWebhook(String webhookData);
}
