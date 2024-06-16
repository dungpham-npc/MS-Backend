package com.cookswp.milkstore.pojo.dtos.ProductModel;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull(message = "Product category can not be null")
    private int categoryID;

    private int postID;

    //validate product name unique
    @NotNull(message = "Product name can not be null")
    private String productName;

    @NotNull(message = "Product description can not be null")
    private String productDescription;

    @NotNull(message = "Image can not be null")
    private String productImage;

    @NotNull(message = "Quantity can not be null")
    @Size(min = 1, message = "Quantity can not lower than 1")
    private int quantity;

    @NotNull(message = "Price can not be null")
    @Size(min = 1, message = "Price can not lower than 1")
    private BigDecimal price;

}
