package com.chibs.payco.payments;

import com.chibs.payco.PaymentProcessor;
import com.chibs.payco.core.TransactionStatus;
import com.chibs.payco.dto.GatewayResponseDto;
import com.chibs.payco.dto.TransactionDto;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentProcessor processor;

    public PaymentService(PaymentRepository paymentRepository, PaymentProcessor processor) {
        this.paymentRepository = paymentRepository;
        this.processor = processor;
    }

    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Map<String, Object> doPayment(TransactionDto payload) {
        String idempotencyKey = ("PYC" + UUID.randomUUID()).replace("-", "");
        if(payload.getTransactionReference() == null){
            payload.setTransactionReference(idempotencyKey);
        }else {
            Optional<Payment> checkExists = paymentRepository.findByTransactionReference(payload.getTransactionReference());
            if(checkExists.isPresent()){
                Payment payment = checkExists.get();
                return Map.of("transaction_id", payment.getTransactionReference(),"status", payment.getStatus(), "message", "Duplicate transaction");
            }
        }

        Payment pay = new Payment();
        pay.setTransactionReference(payload.getTransactionReference());
        pay.setPaymentMethod(payload.getPaymentMethod());
        pay.setCurrency(payload.getCurrency());
        pay.setAmount(payload.getAmount());
        pay.setStatus(TransactionStatus.PENDING);
        var savedPay = paymentRepository.save(pay);

        GatewayResponseDto res = processor.doPayment(payload);
        savedPay.setStatus(TransactionStatus.valueOf(res.getStatus()));
        paymentRepository.save(savedPay);
        //do some Audit Trail here
        return Map.of("transaction_id", res.getTransactionReference(),"status", res.getStatus(), "message", "Payment successful");

    }
}
