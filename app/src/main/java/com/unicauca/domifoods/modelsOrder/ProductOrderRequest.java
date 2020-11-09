package com.unicauca.domifoods.modelsOrder;

public class ProductOrderRequest {
    private int quantity;
    private double total_price_product;
    private int order;
    private int product;

    public ProductOrderRequest() {
    }

    public ProductOrderRequest(int quantity, double total_price_product, int order, int product) {
        this.quantity = quantity;
        this.total_price_product = total_price_product;
        this.order = order;
        this.product = product;
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

    @Override
    public String toString() {
        return "ProductOrderRequest{" +
                "quantity=" + quantity +
                ", total_price_product=" + total_price_product +
                ", order=" + order +
                ", product=" + product +
                '}';
    }
}
