package com.example.lab7.controllers;

import com.example.lab7.HelloApplication;
import com.example.lab7.model.*;
import com.example.lab7.observer_pattern.ChangeEventType;
import com.example.lab7.observer_pattern.Event;
import com.example.lab7.observer_pattern.Observer;
import com.example.lab7.observer_pattern.RepresentationEntityChangeEvent;
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

public class NoLogInSessionController implements Observer<Event> {
    private Service service;
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
    private void onPlaySelected() {
        Play play = playsTable.getSelectionModel().getSelectedItem();
        currentPlayLabel.setText(play.getName());
        cleanSharedTableZone();
        showRepresentationsTable();
        List<Representation> representations = service.getRepresentationsInPeriod(startDateFilter, endDateFilter, play);
        representationTable.getItems().setAll(representations);
    }

    private void initializeEvents() {
        this.playsTable.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, handler -> {
            this.onPlaySelected();
        });
    }
    private void initializeComponents() {
        playNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        initializeRepresentationsTable();
        initializeEvents();
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
    public void initialize (Service service) {
        this.service = service;
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
}
