package com.cookswp.milkstore.enums;

public enum Status {
    IN_CART("Product is currently in the cart"),
    IN_CHECKOUT("Customer is in the checkout process"),
    PAID("Payment has been successfully processed"),
    ORDER_TRANSFER("Order has been transferred for confirmation"),
    IN_DELIVERY("Product is being delivered"),
    CANNOT_DELIVER("Delivery cannot be completed"),
    DELIVERED("Product has been delivered"),
    COMPLETE_EXCHANGE("Completed all flow of buying");

    private final String description;

    Status(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }




}
