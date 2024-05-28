package com.cookswp.milkstore.dtos.request;

import java.io.Serializable;
import java.math.BigDecimal;

public class MilkProductRequest implements Serializable {



    private int productID;
    private int categoryID;
    private int postID;
    private String productName;
    private String productDescription;
    private String productImage;
    private int quantity;
    private BigDecimal price;

}
