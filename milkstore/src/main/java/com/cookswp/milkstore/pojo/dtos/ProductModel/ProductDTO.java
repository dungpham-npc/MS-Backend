package com.cookswp.milkstore.pojo.dtos.ProductModel;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ProductDTO {

    private int categoryID;

    private int postID;

    private String productName;

    private String productDescription;

    private String productImage;

    private int quantity;

    private BigDecimal price;

}
