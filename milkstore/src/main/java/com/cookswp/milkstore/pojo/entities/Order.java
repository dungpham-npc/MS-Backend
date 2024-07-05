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
import java.util.List;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private Status orderStatus;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "failure_reason_note", nullable = true)
    private String failureReasonNote;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "receiver_phone", nullable = false)
    private String receiverPhone;

    @Column(name = "failure_reason", nullable = true)
    private Status failureReason;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "milk_product_id", nullable = false)
    private int productID;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal unitPrice;

    private int carID;
    //And new field

}
