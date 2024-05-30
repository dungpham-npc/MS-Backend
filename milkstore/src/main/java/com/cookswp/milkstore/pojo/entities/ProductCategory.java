package com.cookswp.milkstore.pojo.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "milk_product_category")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "category_name", unique = true, nullable = false)
    private String categoryName;

    public ProductCategory() {

    }

    public ProductCategory(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
