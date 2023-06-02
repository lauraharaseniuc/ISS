package com.example.lab7.model;

import javafx.scene.control.Button;

public class ReservationComponent {
    private String displayText;
    private Reservation reservation;
    private Button button;

    public ReservationComponent(Reservation reservation, Button button) {
        this.reservation = reservation;
        this.button = button;
        this.displayText = "Rezervare pentru spectacolul: "+reservation.getRepresentation().getPlay().getName()
        +"\n in data de " +reservation.getRepresentation().getDate()+ " la ora " + reservation.getRepresentation().getStartsAt()
                +"\n ("+reservation.getNoOfSeats()+"locuri) pe numele: " + reservation.getFirstName()+" "+reservation.getLastName();
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Button getButton() {
        return button;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }
}
