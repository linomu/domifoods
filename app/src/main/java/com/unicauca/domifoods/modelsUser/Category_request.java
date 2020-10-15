package com.unicauca.domifoods.modelsUser;

public class Category_request {
    private int id;
    private String name, description, image, date_creation, state_delete;

    public Category_request() {
    }

    public Category_request(int id, String name, String description, String image, String date_creation, String state_delete) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.date_creation = date_creation;
        this.state_delete = state_delete;
    }

    public Category_request(String name, String image) {
        this.name = name;
        this.image = image;
    }


    // methods for the getterof the  category 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public String getState_delete() {
        return state_delete;
    }

    public void setState_delete(String state_delete) {
        this.state_delete = state_delete;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
