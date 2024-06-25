package com.cookswp.milkstore.pojo.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction_log")
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    Long transaction_id;
    @Column(name = "order_id", nullable = false)
    Long order_id;
    @Column(name = "amount", nullable = false)
    Long amount;
    @Column(name = "bank_code", nullable = false)
    String bankCode;
    @Column(name = "bank_tran_no", nullable = false)
    String bankTranNo;
    @Column(name = "cart_type", nullable = false)
    String cardType;
    @Column(name = "order_info", nullable = false)
    String orderInfo;
    @Column(name = "response_code", nullable = false)
    String responseCode;
    @Column(name= "pay_date", nullable = false)
    String payDate;
    @Column(name = "transaction_no", nullable = false)
    String transactionNo;
    @Column(name = "transaction_status", nullable = false)
    String transactionStatus;


}