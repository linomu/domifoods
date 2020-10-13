package com.unicauca.domifoods.modelsUser;

import java.util.Date;

public class PostsRestaurants {

    private int id, nit,id_admin,hours;
    private long phone_num;
    private String name, address_location, web_page, image,date_creation;
    //private Date date_creation;
    private boolean state_delete, state_disponibility;


    public PostsRestaurants() {
    }

    public PostsRestaurants(int id, int nit, int id_admin, long phone_num, int hours, String name, String address_location, String web_page, String image, String date_creation, boolean state_delete, boolean state_disponibility) {
        this.id = id;
        this.nit = nit;
        this.id_admin = id_admin;
        this.phone_num = phone_num;
        this.hours = hours;
        this.name = name;
        this.address_location = address_location;
        this.web_page = web_page;
        this.image = image;
        this.date_creation = date_creation;
        this.state_delete = state_delete;
        this.state_disponibility = state_disponibility;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }

    public long getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(long phone_num) {
        this.phone_num = phone_num;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress_location() {
        return address_location;
    }

    public void setAddress_location(String address_location) {
        this.address_location = address_location;
    }

    public String getWeb_page() {
        return web_page;
    }

    public void setWeb_page(String web_page) {
        this.web_page = web_page;
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

    public boolean isState_disponibility() {
        return state_disponibility;
    }

    public void setState_disponibility(boolean state_disponibility) {
        this.state_disponibility = state_disponibility;
    }
}
