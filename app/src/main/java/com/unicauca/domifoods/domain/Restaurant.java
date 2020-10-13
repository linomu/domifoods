package com.unicauca.domifoods.domain;

import java.util.Date;

public class Restaurant {

    private int id, nit,id_admin, phone_num,hours;
    private String name, address_location, web_page, image,  date_creation;
    //private Date;
    private boolean state_delete, state_disponibility;

    public Restaurant() {
    }

    public Restaurant(int id, int nit, String name, String address_location, int phone_num, String web_page, int hours, String image, String date_creation, boolean state_delete, boolean state_disponibility, int id_admin) {
        this.id = id;
        this.nit = nit;
        this.name = name;
        this.address_location = address_location;
        this.phone_num = phone_num;
        this.web_page = web_page;
        this.hours = hours;
        this.image = image;
        this.date_creation = date_creation;
        this.state_delete = state_delete;
        this.state_disponibility = state_disponibility;
        this.id_admin = id_admin;
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

    public int getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(int phone_num) {
        this.phone_num = phone_num;
    }

    public String getWeb_page() {
        return web_page;
    }

    public void setWeb_page(String web_page) {
        this.web_page = web_page;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
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

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
    }
}


