package com.example.lab7.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

public class Representation {
    private int id;
    private String startsAt;
    private String date;
    private Play play;
    private Set<Reservation> reservations = new HashSet<>();

    public Representation() {

    }

    public Representation(String startsAt, String date) {
        this.startsAt = startsAt;
        this.date = date;
    }

    public Representation(int id, String startsAt, String date) {
        this.id = id;
        this.startsAt = startsAt;
        this.date = date;
    }

    public Representation(int id, String startsAt, String date, Play play, Set<Reservation> reservations) {
        this.id = id;
        this.startsAt = startsAt;
        this.date = date;
        this.play = play;
        this.reservations = reservations;
    }

    public Representation(String startsAt, String date, Play play, Set<Reservation> reservations) {
        this.startsAt = startsAt;
        this.date = date;
        this.play = play;
        this.reservations = reservations;
    }

    public Representation(int id, String startsAt, String date, Play play) {
        this.id = id;
        this.startsAt = startsAt;
        this.date = date;
        this.play = play;
    }

    public Representation(String startsAt, String date, Play play) {
        this.startsAt = startsAt;
        this.date = date;
        this.play = play;
    }

    public int getId() {
        return id;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public String getDate() {
        return date;
    }

    public Play getPlay() {
        return play;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPlay(Play play) {
        this.play = play;
    }
}
