package com.cookswp.milkstore.pojo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Entity
@Table(name = "milk_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private int productID;

    @Setter
    @Column(name ="category_id", nullable = false)
    private int categoryID;

    @Setter
    @Column(name = "post_id")
    private int postID;

    @Setter
    @Column(name = "product_name", nullable = false, unique = true)
    private String productName;

    @Setter
    @Column(name = "product_description", nullable = false)
    private String productDescription;

    @Setter
    @Column(name = "product_image", nullable = false)
    private String productImage;

    @Setter
    @Column(name = "quantity_in_stock", nullable = false)
    private int quantity;

    @Setter
    @Column(name = "price", nullable = false)
    private BigDecimal price;


    public Product() {}

    public Product(int categoryID, int postID, String productName, String productDescription, String productImage, int quantity, BigDecimal price) {
        this.categoryID = categoryID;
        this.postID = postID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.quantity = quantity;
        this.price = price;
    }
}
