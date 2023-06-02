package com.example.lab7.model;

import java.util.HashSet;
import java.util.Set;

public class Client {
    private int id;
    private String username;
    private String password;
    private Set<Reservation> reservations = new HashSet<>();

    public Client(int id, String username, String password, Set<Reservation> reservations) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.reservations = reservations;
    }

    public Client(String username, String password, Set<Reservation> reservations) {
        this.username = username;
        this.password = password;
        this.reservations = reservations;
    }

    public Client(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Client() {

    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public int getId() {
        return id;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
