package com.cookswp.milkstore.pojo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "milk_product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @Getter@Setter
    private int productID;

    @Setter@Getter
    @Column(name ="category_id", nullable = false)
    private int categoryID;

    @Getter@Setter
    @Column(name = "post_id")
    private int postID;

    @Setter@Getter
    @Column(name = "product_name", nullable = false, unique = true)
    private String productName;

    @Setter@Getter
    @Column(name = "product_description", nullable = false)
    private String productDescription;

    @Setter@Getter
    @Column(name = "product_image", nullable = false)
    private String productImage;

    @Setter@Getter
    @Column(name = "quantity_in_stock", nullable = false)
    private int quantity;

    @Setter@Getter
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Setter@Getter
    @Column(name = "status")
    private boolean status = true;




}
