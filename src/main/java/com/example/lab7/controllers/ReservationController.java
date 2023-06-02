package com.example.lab7.controllers;

import com.example.lab7.model.*;
import com.example.lab7.observer_pattern.ChangeEventType;
import com.example.lab7.observer_pattern.Event;
import com.example.lab7.observer_pattern.Observer;
import com.example.lab7.observer_pattern.ReservationEntityChangeEvent;
import com.example.lab7.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReservationController implements Observer<Event> {
    @FXML
    private Label playNameLabel;
    @FXML
    private Button a41;
    @FXML
    private Button a42;
    @FXML
    private Button a43;
    @FXML
    private Button a44;
    @FXML
    private Button a45;
    @FXML
    private Button a46;
    @FXML
    private Button a47;
    @FXML
    private Button a48;
    @FXML
    private Button a49;
    @FXML
    private Button a31;
    @FXML
    private Button a32;
    @FXML
    private Button a33;
    @FXML
    private Button a34;
    @FXML
    private Button a35;
    @FXML
    private Button a36;
    @FXML
    private Button a37;
    @FXML
    private Button a38;
    @FXML
    private Button a21;
    @FXML
    private Button a22;
    @FXML
    private Button a23;
    @FXML
    private Button a24;
    @FXML
    private Button a25;
    @FXML
    private Button a26;
    @FXML
    private Button a27;
    @FXML
    private Button a11;
    @FXML
    private Button a12;
    @FXML
    private Button a13;
    @FXML
    private Button a14;
    @FXML
    private Button a15;
    @FXML
    private Button a16;
    @FXML
    private TextField firstNameTF;
    @FXML
    private TextField lastNameTF;
    @FXML
    private TextField phoneTF;
    @FXML
    private TextField emailTF;
    @FXML
    private Label errorLabel;
    @FXML
    private Pane roomConfigurationPane;
    private Scene scene;

    private Representation representation;
    private Service service;
    private Client client;
    private List<Button> selectedSeats = new ArrayList<>();
    private List<String> selectedSeatsIds = new ArrayList<>();

    @Override
    public void update(Event event) {
        if (event instanceof ReservationEntityChangeEvent) {
            if (event.getEventType() == ChangeEventType.RESERVATION_CANCELLED) {
                Reservation reservation = ((ReservationEntityChangeEvent) event).getReservation();
                if (reservation.getRepresentation().getId()== representation.getId()) {
                    Set<ReservedSeat> reservedSeats = reservation.getReservedSeats();
                    reservedSeats.forEach(reservedSeat -> {
                        Seat referredSeat = reservedSeat.getReferredSeat();
                        String zone = referredSeat.getZone();
                        String row = ((Integer)referredSeat.getRow()).toString();
                        String seatNo = ((Integer)referredSeat.getSeatNo()).toString();
                        Button button = (Button) scene.lookup("#"+zone+row+seatNo);
                        button.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
                    });
                }
            }
            if (event.getEventType() == ChangeEventType.RESERVATION_MADE) {
                Reservation reservation = ((ReservationEntityChangeEvent) event).getReservation();
                if (reservation.getRepresentation().getId()== representation.getId()) {
                    Set<ReservedSeat> reservedSeats = reservation.getReservedSeats();
                    reservedSeats.forEach(reservedSeat -> {
                        Seat referredSeat = reservedSeat.getReferredSeat();
                        String zone = referredSeat.getZone();
                        String row = ((Integer)referredSeat.getRow()).toString();
                        String seatNo = ((Integer)referredSeat.getSeatNo()).toString();
                        Button button = (Button) scene.lookup("#"+zone+row+seatNo);
                        button.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(0), new Insets(0))));
                    });
                }
            }
        }
    }

    private void initializeComponents() {
        this.playNameLabel.setText(representation.getPlay().getName()+"\n"+representation.getDate()+" ora: "+representation.getStartsAt());
        this.playNameLabel.setTextFill(Color.AQUA);
        a41.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a42.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a43.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a44.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a45.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a46.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a47.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a48.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a49.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a31.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a32.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a33.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a34.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a35.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a36.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a37.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a38.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a21.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a22.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a23.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a24.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a25.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a26.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a27.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a11.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a12.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a13.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a14.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a15.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        a16.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        initializeSeatsState();
    }
    public void initialize(Service service, Client client, Representation representation, Scene scene) {
        this.representation=representation;
        this.service=service;
        this.client=client;
        this.scene=scene;
        initializeComponents();
    }

    private void initializeSeatsState() {
        List<Reservation> reservations = service.getReservationsForRepresentation(representation);
        reservations.forEach(reservation -> {
            List<Seat> reservedSeats = service.getReservedSeatsForReservation(reservation);
            reservedSeats.forEach(seat -> {
                String zone = seat.getZone();
                String row = ((Integer)seat.getRow()).toString();
                String seatNo = ((Integer)seat.getSeatNo()).toString();
                Button button = (Button) scene.lookup("#"+zone+row+seatNo);
                button.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(0), new Insets(0))));
            });
        });
    }

    public void onSeatChosen(ActionEvent actionEvent) {
        errorLabel.setText("");
        Button button = (Button) actionEvent.getSource();
        if (button.getBackground().getFills().get(0).equals(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0)))) {
            selectedSeats.add(button);
            selectedSeatsIds.add(button.getId());
            button.setBackground(new Background(new BackgroundFill(Color.GOLD, new CornerRadii(0), new Insets(0))));
        }
        else if (button.getBackground().getFills().get(0).equals(new BackgroundFill(Color.RED, new CornerRadii(0), new Insets(0)))) {
            errorLabel.setText("Loc deja rezervat! Alegeti un alt loc!");
        }
        else {
            selectedSeats.remove(button);
            selectedSeatsIds.remove(button.getId());
            button.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0))));
        }
    }

    public void onReserveClicked(ActionEvent actionEvent) {
        errorLabel.setText("");
        String fname = firstNameTF.getText();
        String lname = lastNameTF.getText();
        String phone = phoneTF.getText();
        String email = emailTF.getText();
        if (fname.length()==0) {
            errorLabel.setText("Introduceti o valoare pentru campul nume!");
            return;
        }
        if (lname.length()==0) {
            errorLabel.setText("Introduceti o valoare pentru campul prenume!");
            return;
        }
        if (phone.length()==0) {
            errorLabel.setText("Introduceti o valoare pentru campul numar de telefon!");
            return;
        }
        if (email.length()==0) {
            errorLabel.setText("Introduceti o valoare pentru campul adresa de email!");
            return;
        }
        int noOfSeats = selectedSeats.size();
        if (noOfSeats == 0) {
            errorLabel.setText("Selectati unul sau mai multe locuri pentru a face o rezervare!");
            return;
        }
        Reservation reservation = service.makeReservation(fname, lname, phone, email, client, representation, noOfSeats, selectedSeatsIds);
        client.getReservations().add(reservation);
        initializeSeatsState();
    }
}
