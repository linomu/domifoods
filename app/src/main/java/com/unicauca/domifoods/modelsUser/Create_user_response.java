package com.unicauca.domifoods.modelsUser;

import retrofit2.http.Field;

public class Create_user_response {
   private String answer, id, token;

    public Create_user_response() {
    }

    public Create_user_response(String answer, String id, String token) {
        this.answer = answer;
        this.id = id;
        this.token = token;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Create_user_response{" +
                "answer='" + answer + '\'' +
                ", id='" + id + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
