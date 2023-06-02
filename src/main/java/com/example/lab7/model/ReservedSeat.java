package com.example.lab7.model;

public class ReservedSeat {
    private int id;
    private Reservation reservation;
    private Seat referredSeat;

    public ReservedSeat() {

    }

    public ReservedSeat(int id, Reservation reservation, Seat referredSeat) {
        this.id = id;
        this.reservation = reservation;
        this.referredSeat = referredSeat;
    }

    public ReservedSeat(Reservation reservation, Seat referredSeat) {
        this.reservation = reservation;
        this.referredSeat = referredSeat;
    }

    public int getId() {
        return id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Seat getReferredSeat() {
        return referredSeat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setReferredSeat(Seat referredSeat) {
        this.referredSeat = referredSeat;
    }
}
