package com.example.lab7.observer_pattern;


import com.example.lab7.model.Reservation;

public class ReservationEntityChangeEvent extends Event{
    private Reservation reservation;
    public ReservationEntityChangeEvent(ChangeEventType changeEventType, Reservation reservation) {
        super(changeEventType);
        this.reservation=reservation;
    }

    public Reservation getReservation() {
        return reservation;
    }
}
