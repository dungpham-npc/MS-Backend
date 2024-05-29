package com.cookswp.milkstore.pojo.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "milk_product")
public class MilkProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private int productID;

    @Column(name ="category_id", nullable = false)
    private int categoryID;

    @Column(name = "post_id")
    private int postID;

    @Column(name = "product_name", nullable = false, unique = true)
    private String productName;

    @Column(name = "product_description", nullable = false)
    private String productDescription;

    @Column(name = "product_image", nullable = false)
    private String productImage;

    @Column(name = "quantity_in_stock", nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;


    public MilkProduct() {}

    public MilkProduct(int categoryID, int postID, String productName, String productDescription, String productImage, int quantity, BigDecimal price) {
        this.categoryID = categoryID;
        this.postID = postID;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductID() {
        return productID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}