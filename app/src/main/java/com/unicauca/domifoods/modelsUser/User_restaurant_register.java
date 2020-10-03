package com.unicauca.domifoods.modelsUser;

public class User_restaurant_register {
    private String user, document_type, document, first_name, last_name, genre, phone_num, date_of_birth, email_address, address_location;

    public User_restaurant_register(String user, String document_type, String document, String first_name, String last_name, String genre, String phone_num, String date_of_birth, String email_address, String address_location) {
        this.user = user;
        this.document_type = document_type;
        this.document = document;
        this.first_name = first_name;
        this.last_name = last_name;
        this.genre = genre;
        this.phone_num = phone_num;
        this.date_of_birth = date_of_birth;
        this.email_address = email_address;
        this.address_location = address_location;
    }

    public User_restaurant_register() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDocument_type() {
        return document_type;
    }

    public void setDocument_type(String document_type) {
        this.document_type = document_type;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getAddress_location() {
        return address_location;
    }

    public void setAddress_location(String address_location) {
        this.address_location = address_location;
    }
}
