package com.cookswp.milkstore.pojo.entities;

import com.cookswp.milkstore.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table (name = "shopping_cart")
@Getter
@Setter
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", unique = true, nullable = false)
    private int userId;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoppingCartItem> items;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status; // Add this line to keep track of the cart status



}
