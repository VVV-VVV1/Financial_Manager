package com.example.myapplication.models;

public class User {
    public String email, name, password;
    float sum;
    private boolean admin;
    public User(){
    }

    public User(String email, String name, String password, float sum) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.sum = sum;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}
