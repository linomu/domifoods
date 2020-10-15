package com.unicauca.domifoods.modelsRestaurantLino;

public class RestaurantResponse {
    private int id,nit,phone_num,id_admin;
    private String name, address_location,web_page,hours,image,date_creation;
    private boolean state_delete,state_disponibility;

    public RestaurantResponse() {
    }

    public RestaurantResponse(int id, int nit, int phone_num, int id_admin, String name, String address_location, String web_page, String hours, String image, String date_creation, boolean state_delete, boolean state_disponibility) {
        this.id = id;
        this.nit = nit;
        this.phone_num = phone_num;
        this.id_admin = id_admin;
        this.name = name;
        this.address_location = address_location;
        this.web_page = web_page;
        this.hours = hours;
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

    public int getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(int phone_num) {
        this.phone_num = phone_num;
    }

    public int getId_admin() {
        return id_admin;
    }

    public void setId_admin(int id_admin) {
        this.id_admin = id_admin;
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

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
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

    @Override
    public String toString() {
        return "RestaurantResponse{" +
                "id=" + id +
                ", nit=" + nit +
                ", phone_num=" + phone_num +
                ", id_admin=" + id_admin +
                ", name='" + name + '\'' +
                ", address_location='" + address_location + '\'' +
                ", web_page='" + web_page + '\'' +
                ", hours='" + hours + '\'' +
                ", image='" + image + '\'' +
                ", date_creation='" + date_creation + '\'' +
                ", state_delete=" + state_delete +
                ", state_disponibility=" + state_disponibility +
                '}';
    }
}
