package com.example.lab7.controllers;

import com.example.lab7.HelloApplication;
import com.example.lab7.model.*;
import com.example.lab7.observer_pattern.*;
import com.example.lab7.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientSessionController implements Observer<Event> {
    private Service service;
    private Client clientLoggedIn;

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Label errorLabel;
    @FXML
    private TableView<Play> playsTable;
    @FXML
    private TableColumn<String, Play> playNameColumn;
    @FXML
    private TableColumn<String, Play> playDescriptionColumn;
    @FXML
    private Label currentPlayLabel;
    private TableView<Representation> representationTable;
    private TableColumn<Representation, String> startTimeColumn;
    private TableColumn<Representation, String> dateColumn;
    private TableView<ReservationComponent> reservationsTable;
    private TableColumn<ReservationComponent, String> reservationDataColumn;
    private TableColumn<ReservationComponent, Button> cancelButtonColumn;
    @FXML
    private VBox tableBox;
    private LocalDate startDateFilter;
    private LocalDate endDateFilter;

    @Override
    public void update(Event event) {
        if (event instanceof RepresentationEntityChangeEvent) {
            RepresentationEntityChangeEvent representationEntityChangeEvent = (RepresentationEntityChangeEvent) event;
            if (representationEntityChangeEvent.getEventType()== ChangeEventType.REPRESENTATION_ADDED) {
                Play play = representationEntityChangeEvent.getRepresentation().getPlay();
                List<Representation> representations = service.getRepresentationsInPeriod(startDateFilter, endDateFilter, play);
                representationTable.getItems().setAll(representations);
            }
        }
    }
    private void cleanSharedTableZone() {
        try {
            tableBox.getChildren().remove(playsTable);
        } catch (Exception ex) {

        }
        try {
            tableBox.getChildren().remove(representationTable);
        } catch (Exception ex) {

        }
        try {
            tableBox.getChildren().remove(reservationsTable);
        } catch (Exception ex) {

        }
    }
    private void showRepresentationsTable() {
        try {
            tableBox.getChildren().add(representationTable);
        }catch (Exception ex) {

        }
    }
    private void showPlaysTable() {
        try {
            tableBox.getChildren().add(playsTable);
        }catch (Exception ex) {

        }
    }
    private void showReservationsTable() {
        try {
            tableBox.getChildren().add(reservationsTable);
        }catch (Exception ex) {

        }
    }
    private void onPlaySelected() {
        Play play = playsTable.getSelectionModel().getSelectedItem();
        currentPlayLabel.setText(play.getName());
        cleanSharedTableZone();
        showRepresentationsTable();
        List<Representation> representations = service.getRepresentationsInPeriod(startDateFilter, endDateFilter, play);
        representationTable.getItems().setAll(representations);
    }

    private void openReservationWindow(Representation selectedRepresentation) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("client-reservation-view.fxml"));
            Parent root = loader.load();
            ReservationController reservationController = loader.getController();
            Scene logged_in_scene = new Scene(root);
            reservationController.initialize(service, clientLoggedIn, selectedRepresentation, logged_in_scene);
            service.addObserver(reservationController);
            logged_in_scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("css_styles/reservation_styles.css")).toExternalForm());
            //Stage main_stage=(Stage)this.logInButton.getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(logged_in_scene);
            newStage.show();
        }catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
    private void onRepresentationSelected () {
        Representation representation = representationTable.getSelectionModel().getSelectedItem();
        Play selectedPlay = playsTable.getSelectionModel().getSelectedItem();
        representation.setPlay(selectedPlay);
        openReservationWindow(representation);
    }

    private void initializeEvents() {
        this.playsTable.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, handler -> {
            this.onPlaySelected();
        });
        this.representationTable.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, handler -> {
            this.onRepresentationSelected();
        });
    }
    private void initializeComponents() {
        playNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        initializeRepresentationsTable();
        initialiseReservationsTable();
        initializeEvents();
    }

    private void initialiseReservationsTable() {
        reservationsTable = new TableView<>();
        reservationDataColumn = new TableColumn<>();
        cancelButtonColumn = new TableColumn<>();
        reservationDataColumn.setCellValueFactory(new PropertyValueFactory<>("displayText"));
        cancelButtonColumn.setCellValueFactory(new PropertyValueFactory<>("button"));
        reservationDataColumn.setText("DATE REZERVARE");
        reservationsTable.getColumns().add(reservationDataColumn);
        reservationsTable.getColumns().add(cancelButtonColumn);
        reservationsTable.setPrefWidth(372);
        reservationsTable.setPrefHeight(383);
        reservationDataColumn.setPrefWidth(this.reservationsTable.getPrefWidth() * 0.80);
        cancelButtonColumn.setPrefWidth(this.reservationsTable.getPrefWidth() * 0.20);
    }

    private void initializeRepresentationsTable() {
        this.representationTable = new TableView<Representation>();
        this.startTimeColumn = new TableColumn<Representation, String>();
        this.dateColumn = new TableColumn<Representation, String>();
        this.startTimeColumn.setCellValueFactory(new PropertyValueFactory<Representation, String>("startsAt"));
        this.dateColumn.setCellValueFactory(new PropertyValueFactory<Representation, String>("date"));
        this.startTimeColumn.setText("ORA DE INCEPUT");
        this.dateColumn.setText("DATA");
        this.representationTable.getColumns().add(this.startTimeColumn);
        this.representationTable.getColumns().add(this.dateColumn);
        this.representationTable.setPrefWidth(372);
        this.representationTable.setPrefHeight(383);
        this.startTimeColumn.setPrefWidth(this.representationTable.getPrefWidth() / 2);
        this.dateColumn.setPrefWidth(this.representationTable.getPrefWidth() / 2);
    }
    public void initialize (Service service, Client clientLoggedIn) {
        this.service = service;
        this.clientLoggedIn=clientLoggedIn;
        initializeComponents();
    }

    public void onFilterPlays(ActionEvent actionEvent) {
        cleanSharedTableZone();
        showPlaysTable();
        errorLabel.setText("");
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        if (startDate == null || endDate == null) {
            errorLabel.setText("Selectati valori pentru data de inceput si data de final a filtrarii!");
            return;
        }
        if (startDate.compareTo(endDate) > 0) {
            errorLabel.setText("Data de inceput trebuie sa fie cronologic mai mica decat data de final!");
            return;
        }
        startDateFilter = startDate;
        endDateFilter = endDate;
        List<Play> plays = service.getPlaysInPeriod(startDate, endDate);
        if (plays.size() == 0) {
            errorLabel.setText("Nu exista spectacole cu reprezentatii in perioada selectata!");
            return;
        }
        playsTable.getItems().setAll(plays);
    }



    private void onReservationToBeCancelledChosen(Reservation reservation) {
        clientLoggedIn.getReservations().remove(reservation);
        service.deleteReservation(reservation);
        populateReservationsTable();
    }

    private void populateReservationsTable() {
        List<ReservationComponent> reservationComponentList = new ArrayList<>();
        clientLoggedIn.getReservations().forEach(reservation -> {
            Button cancelButton = new Button("Anuleaza");
            cancelButton.setPrefWidth(100);
            cancelButton.setPrefHeight(50);
            cancelButton.setOnAction(event -> {
                this.onReservationToBeCancelledChosen(reservation);
            });
            ReservationComponent reservationComponent = new ReservationComponent(reservation, cancelButton);
            reservationComponentList.add(reservationComponent);
        });
        reservationsTable.getItems().setAll(reservationComponentList);
    }
    public void onCancelReservation(ActionEvent actionEvent) {
        cleanSharedTableZone();
        showReservationsTable();
        populateReservationsTable();
    }
}
