package com.unicauca.domifoods.modelsProduct;

public class ProductShoppingCart {
    private int id, cant;
    private String urlImagen, name;
    private double price, subtotal;

    public ProductShoppingCart() {
    }

    public ProductShoppingCart(int id, int cant, String urlImagen, String name, double price, double subtotal) {
        this.id = id;
        this.cant = cant;
        this.urlImagen = urlImagen;
        this.name = name;
        this.price = price;
        this.subtotal = subtotal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "ProductShoppingCart{" +
                "id=" + id +
                ", cant=" + cant +
                ", urlImagen='" + urlImagen + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", subtotal=" + subtotal +
                '}';
    }
}
