package com.cookswp.milkstore.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    //Post Error Code Exception:
    TITLE_NULL(1, "Title cannot be null"),
    TITLE_BLANK(2, "Title cannot be blank"),
    TITLE_EMPTY(3, "Title cannot be empty"),
    CONTENT_NULL(4, "Content cannot be null"),
    CONTENT_BLANK(5, "Content cannot be blank"),
    CONTENT_EMPTY(6, "Content cannot be empty"),
    POST_ID_NOT_FOUND(7, "Post must be existed in the system"),
    POST_TITLE_ERROR(8, "Post title can not be empty"),
    POST_CONTENT_ERROR(9, "Post content can not be empty"),
    ALL_POST_EMPTY(10, "Haven't any posts yet"),
    POST_EXISTS(11, "Post already exists"),
    POST_TITLE_EXISTS(12, "Post title must be unique"),
    POST_CONTENT_OFFENSIVE_WORD(13, "Post content contain offensive word"),
    //PRODUCT ERROR CODE
    CATEGORY_NOT_EXISTED(14, "Category must be existed"),
    INVALID_PRICE(15, "Price cannot be less than 0"),
    PRODUCT_DESCRIPTION_IS_NULL(16, "Product description is required"),
    PRODUCT_NAME_EXISTS(17, "Product name already exists in the system"),
    PRODUCT_IMAGE_INVALID(18, "Invalid product image"),
    PRODUCT_QUANTITY_INVALID(19, "Quantity cannot be less than 0"),
    PRODUCT_NOT_FOUND(20, "Product not found"),
    PRODUCT_LIST_NOT_FOUND(21, "Product list empty"),
    PRODUCT_ID_NOT_FOUND(22, "Product ID not exists")
    ;

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
