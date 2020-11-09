package com.unicauca.domifoods.modelsUser;

public class Client_detail_response {

    private int  id;
    private String id_user_restaurant;

    public Client_detail_response() {
    }

    public Client_detail_response(int id, String id_user_restaurant) {
        this.id = id;
        this.id_user_restaurant = id_user_restaurant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_user_restaurant() {
        return id_user_restaurant;
    }

    public void setId_user_restaurant(String id_user_restaurant) {
        this.id_user_restaurant = id_user_restaurant;
    }

    @Override
    public String toString() {
        return "Client_detail_response{" +
                "id=" + id +
                ", id_user_restaurant='" + id_user_restaurant + '\'' +
                '}';
    }
}
