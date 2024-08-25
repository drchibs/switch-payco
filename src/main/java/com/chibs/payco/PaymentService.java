package com.chibs.payco;

import com.chibs.payco.dto.TransactionDto;
import com.chibs.payco.gateways.MockGateway;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

@Service
public class PaymentService {

    private final MockGateway mockGateway;

    public PaymentService(MockGateway mockGateway) {
        this.mockGateway = mockGateway;
    }

    public Map<String, Object> doPayment(TransactionDto transaction) {
        if (transaction == null || !isValidTransaction(transaction)) {
            throw new IllegalArgumentException("Invalid transaction data");
        }

        String securePayload = encryptPayload(transaction);

        Map<String, Object> gatewayResponse = mockGateway.processPayment(securePayload);
        return gatewayResponse;
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
