package com.unicauca.domifoods.modelsProduct;

public class ProductResponse {
    //attributes of  response  preoduc  in the service web
    private int id;
    private String name,description,image,date_creation;
    private int price, category;
    private boolean state_delete,state_disponibility;

    //builder
    public ProductResponse() {
    }

    public ProductResponse(int id, String name, String description, String image, String date_creation, int price, int category, boolean state_delete, boolean state_disponibility) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.date_creation = date_creation;
        this.price = price;
        this.category = category;
        this.state_delete = state_delete;
        this.state_disponibility = state_disponibility;
    }
    //methods getts of preoducts
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public boolean isState_delete() {
        return state_delete;
    }

    public void setState_delete(boolean state_delete) {
        this.state_delete = state_delete;
    }

    public boolean isState_disponibility() {
        return state_disponibility;
    }

    public void setState_disponibility(boolean state_disponibility) {
        this.state_disponibility = state_disponibility;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", date_creation='" + date_creation + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", state_delete=" + state_delete +
                ", state_disponibility=" + state_disponibility +
                '}';
    }
}
