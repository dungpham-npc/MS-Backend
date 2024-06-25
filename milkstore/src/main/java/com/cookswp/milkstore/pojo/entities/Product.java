package com.cookswp.milkstore.pojo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Setter@Getter
@Table(name = "milk_product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "manu_date", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date manuDate;

    @Column(name = "expi_date", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date expiDate;

    @Column(name = "status")
    private boolean status = true;

    //return true if manu date is before expi date
    public boolean dateBefore(Date manuDate, Date expiDate){
        return manuDate.before(expiDate);
    }



}
