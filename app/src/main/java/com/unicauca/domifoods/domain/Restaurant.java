package com.unicauca.domifoods.domain;

public class Restaurant {
    private int id;
    private String name, photo;
    //Agregar los demás campos que vengan desde la tabla

    public Restaurant() {
    }

    public Restaurant(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }

    public Restaurant(int id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }


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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
