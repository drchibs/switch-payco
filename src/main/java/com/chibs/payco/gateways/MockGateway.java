package com.chibs.payco.gateways;

import com.chibs.payco.PaymentGateway;
import com.chibs.payco.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class MockGateway implements PaymentGateway {

    //protected boolean useEncryption = false;

    @Override
    public Map<String, Object> processPayment(String payload) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("Payment event triggered >>>>>>>>>{}", payload);
        return Map.of("status", "success", "message", "Payment Successful");
    }


    @Override
    public Object handleWebhook(String webhookData) {
        log.info("Webhook received: " + webhookData);
        return Map.of("status", "ok");
    }
}
