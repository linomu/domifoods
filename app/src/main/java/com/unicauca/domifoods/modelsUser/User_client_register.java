package com.unicauca.domifoods.modelsUser;

public class User_client_register {
    private String id_user_restaurant;

    public User_client_register() {
    }

    public User_client_register(String id_user_restaurant) {
        this.id_user_restaurant = id_user_restaurant;
    }

    public String getId_user_restaurant() {
        return id_user_restaurant;
    }

    public void setId_user_restaurant(String id_user_restaurant) {
        this.id_user_restaurant = id_user_restaurant;
    }
}
