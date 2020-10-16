package com.unicauca.domifoods.modelsCategory;

import java.util.List;

public class CategoriesResponse {
    //attributes of categories  the restaurant
    private int id;
    private String name,description,image,date_creation;
    private boolean state_delete;
    private int restaurant;
    //builder
    public CategoriesResponse() {
    }

    public CategoriesResponse(int id, String name, String description, String image, String date_creation, boolean state_delete, int restaurant) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.date_creation = date_creation;
        this.state_delete = state_delete;
        this.restaurant = restaurant;
    }
    //methods gets of categories
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

    public boolean isState_delete() {
        return state_delete;
    }

    public void setState_delete(boolean state_delete) {
        this.state_delete = state_delete;
    }

    public int getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(int restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "CategoriesResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", date_creation='" + date_creation + '\'' +
                ", state_delete=" + state_delete +
                ", restaurant=" + restaurant +
                '}';
    }
}
