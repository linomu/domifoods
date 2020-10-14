package com.unicauca.domifoods.modelsUser;

public class Product_request {

    //este es el objeto gsobn para traer la informacion de  el  producto
    private int id, id_category, id_restaurant;
    private String name, description, image;
    private Float price;

    public Product_request() {
    }
    //constructores
    public Product_request(int id, int id_category, int id_restaurant, String name, String description, String image, Float price) {
        this.id = id;
        this.id_category = id_category;
        this.id_restaurant = id_restaurant;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public Product_request(String name, String image, Float price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }
    //metodos get
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getId_restaurant() {
        return id_restaurant;
    }

    public void setId_restaurant(int id_restaurant) {
        this.id_restaurant = id_restaurant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
