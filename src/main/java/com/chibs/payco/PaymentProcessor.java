package com.chibs.payco;

import com.chibs.payco.core.TransactionStatus;
import com.chibs.payco.core.TransactionType;
import com.chibs.payco.dto.GatewayResponseDto;
import com.chibs.payco.dto.TransactionDto;
import com.chibs.payco.gateways.MockGateway;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

@Service
public class PaymentProcessor {

    private final MockGateway mockGateway;

    public PaymentProcessor(MockGateway mockGateway) {
        this.mockGateway = mockGateway;
    }

    public GatewayResponseDto doPayment(TransactionDto transaction) {
        if (transaction == null || !isValidTransaction(transaction)) {
            throw new IllegalArgumentException("Invalid transaction data");
        }

        if(transaction.getPaymentMethod() == TransactionType.USSD){
            //simulate use of a dedicated gateway
            return GatewayResponseDto.builder()
                    .amount(String.valueOf(transaction.getAmount()))
                    .currency(transaction.getCurrency())
                    .transactionReference(transaction.getTransactionReference())
                    .type(TransactionType.USSD.name())
                    .status(TransactionStatus.SUCCESSFUL.name())
                    .build();
        }

        //String securePayload = encryptPayload(transaction); - hold on with this

        return mockGateway.processPayment(transaction);
    }

    private boolean isValidTransaction(TransactionDto transaction) {
        return transaction.getAmount() > 0 && transaction.getPaymentMethod() != null;
    }

    private String encryptPayload(TransactionDto transaction) {
        try {
            String payload = transaction.toString();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(payload.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }
}
