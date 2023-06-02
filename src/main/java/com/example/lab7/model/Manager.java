package com.example.lab7.model;

public class Manager {
    private int id;
    private String name;
    private String username;
    private String password;

    public Manager(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Manager(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public Manager() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
