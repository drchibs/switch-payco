package com.chibs.payco.payments;

import com.chibs.payco.core.BaseEntity;
import com.chibs.payco.core.TransactionStatus;
import com.chibs.payco.core.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends BaseEntity {
    private double amount;
    private TransactionType paymentMethod;
    private String currency;
    private String transactionReference;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}