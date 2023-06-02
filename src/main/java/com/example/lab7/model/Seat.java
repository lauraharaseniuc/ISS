package com.example.lab7.model;

import java.util.HashSet;
import java.util.Set;

public class Seat {
    private int id;
    private String zone;
    private int row;
    private int seatNo;
    private Set<ReservedSeat> reservedSeats = new HashSet<>();

    public Seat() {

    }

    public Seat(String zone, int row, int seatNo, Set<ReservedSeat> reservedSeats) {
        this.zone = zone;
        this.row = row;
        this.seatNo = seatNo;
        this.reservedSeats = reservedSeats;
    }

    public Seat(int id, String zone, int row, int seatNo, Set<ReservedSeat> reservedSeats) {
        this.id = id;
        this.zone = zone;
        this.row = row;
        this.seatNo = seatNo;
        this.reservedSeats = reservedSeats;
    }

    public Seat(int id, String zone, int row, int seatNo) {
        this.id = id;
        this.zone = zone;
        this.row = row;
        this.seatNo = seatNo;
    }

    public Seat(String zone, int row, int seatNo) {
        this.zone = zone;
        this.row = row;
        this.seatNo = seatNo;
    }

    public void setReservedSeats(Set<ReservedSeat> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public Set<ReservedSeat> getReservedSeats() {
        return reservedSeats;
    }

    public int getId() {
        return id;
    }

    public String getZone() {
        return zone;
    }

    public int getRow() {
        return row;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }
}
