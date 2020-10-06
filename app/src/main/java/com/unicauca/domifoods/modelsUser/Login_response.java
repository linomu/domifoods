package com.unicauca.domifoods.modelsUser;

public class Login_response {
    private String  answer, document, expiry,token;

    public Login_response() {
    }

    public Login_response(String answer, String document, String expiry, String token) {
        this.answer = answer;
        this.document = document;
        this.expiry = expiry;
        this.token = token;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
