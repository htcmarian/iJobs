package com.example.ijobs.data;

public class User {
    private String email;
    private String userId;

    public User(String email){
        this.email = email;
    }

    public User(String userId, String email) {

        this.userId = userId;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
