package com.unicauca.domifoods.modelsUser;

public class Login_request {
    private String  username, password;

    public Login_request() {
    }

    public Login_request(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
