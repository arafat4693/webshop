package com.dslabb1.dslabb1.controller;

public class UserInfo {
    private int id;
    private String username;
    private String role;

    public UserInfo(int id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
