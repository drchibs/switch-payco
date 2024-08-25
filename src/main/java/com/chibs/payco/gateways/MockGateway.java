package com.chibs.payco.gateways;

import com.chibs.payco.PaymentGateway;
import com.chibs.payco.core.TransactionStatus;
import com.chibs.payco.dto.GatewayResponseDto;
import com.chibs.payco.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class MockGateway implements PaymentGateway {

    //protected boolean useEncryption = false;

    @Override
    public GatewayResponseDto processPayment(TransactionDto data) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("Payment event triggered >>>>>>>>>{}", data);
        return GatewayResponseDto.builder()
                .amount(String.valueOf(data.getAmount()))
                .currency(data.getCurrency())
                .transactionReference(data.getTransactionReference())
                .type(data.getPaymentMethod().name())
                .status(TransactionStatus.SUCCESSFUL.name())
                .build();
    }


    @Override
    public Object handleWebhook(String webhookData) {
        log.info("Webhook received: " + webhookData);
        return Map.of("status", "ok");
    }
}
