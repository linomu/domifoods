package com.unicauca.domifoods.modelsOrder;

public class ProductOrderResponse {
    private int id;
    private int quantity;
    private double total_price_product;
    private int order;
    private int product;

    public ProductOrderResponse(int id, int quantity, double total_price_product, int order, int product) {
        this.id = id;
        this.quantity = quantity;
        this.total_price_product = total_price_product;
        this.order = order;
        this.product = product;
    }

    public ProductOrderResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal_price_product() {
        return total_price_product;
    }

    public void setTotal_price_product(double total_price_product) {
        this.total_price_product = total_price_product;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getProduct() {
        return product;
    }

    public void setProduct(int product) {
        this.product = product;
    }
}
