package com.goats.chatbrewery.modelclass;

import java.util.ArrayList;

public class UserModel {

    private String name;
    private String phone;
    private String email;
    private String dob;
    private String sex;
    private ArrayList<String> friends = new ArrayList<>();

    public UserModel() {
    }

    public UserModel(String name, String phone, String email, ArrayList<String> friends) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.friends = friends;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<String> getFriends() { return friends; }
}
