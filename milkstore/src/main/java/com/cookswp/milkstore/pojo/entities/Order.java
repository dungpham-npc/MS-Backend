package com.cookswp.milkstore.pojo.entities;

import com.cookswp.milkstore.enums.FailureReason;
import com.cookswp.milkstore.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table (name = "order")
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "customer_payment_method_id", nullable = false)
    private int customerPaymentMethodId;

    @Column(name = "shipping_address", nullable = false)
    private int shippingAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private Status status;

    @Column(name = "voucher_id")
    private Integer voucherId;

    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name = "shipping_fee", nullable = false)
    private BigDecimal shippingFee;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "shipping_code")
    private String shippingCode;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "receiver_phone", nullable = false)
    private String receiverPhone;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "failure_reason")
    private FailureReason failureReason;

    @Column(name = "failure_reason_note")
    private String failureReasonNote;

}
