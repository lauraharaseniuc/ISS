package com.example.lab7.controllers;

import com.example.lab7.model.Manager;
import com.example.lab7.model.Play;
import com.example.lab7.observer_pattern.Event;
import com.example.lab7.observer_pattern.Observer;
import com.example.lab7.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class ManagerSessionController implements Observer<Event> {
    private Service service;
    private Manager managerLoggedIn;
    @FXML
    private ComboBox<Play> playCB;
    @FXML
    private TextField nameTF;
    @FXML
    private TextArea descriptionTA;
    @FXML
    private TextField authorTF;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField timeTF;
    @FXML
    private Label errorLabel;

    private void initializePLaysList(){
        List<Play> plays = service.getAllPlays();
        playCB.getItems().setAll(plays);
    }
    private void initializeComponents() {
        playCB.setCellFactory(param -> new ListCell<Play>(){
            @Override
            protected void updateItem(Play item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
    }
    public void initialize(Service service, Manager manager) {
        this.service =service;
        this.managerLoggedIn=manager;
        initializeComponents();
        initializePLaysList();
    }

    @Override
    public void update(Event event) {

    }

    public void onAddPlay(ActionEvent actionEvent) {
        errorLabel.setText("");
        String name = nameTF.getText();
        String description = descriptionTA.getText();
        String author = authorTF.getText();
        if (name.length()==0) {
            errorLabel.setText("Campul dedicat numelui spectacolului nu poate fi gol!");
            return;
        }
        if (author.length()==0) {
            errorLabel.setText("Campul dedicat autorului/autorilor spectacolului nu poate fi gol!");
            return;
        }
        service.addPlay(name, description, author);
        initializePLaysList();
    }

    public void onAddRepresentation(ActionEvent actionEvent) {
        errorLabel.setText("");
        Play selectedPlay = playCB.getValue();
        if (selectedPlay==null) {
            errorLabel.setText("Selectati un spectacol!");
            return;
        }
        String date = datePicker.getValue().toString();
        String startTime = timeTF.getText();
        if (date.length()==0) {
            errorLabel.setText("Selectati data reprezentatiei!");
            return;
        }
        if (startTime.length()==0) {
            errorLabel.setText("Selectati ora de inceput a reprezentatiei!");
            return;
        }
        service.addRepresentation(startTime, date, selectedPlay);
    }
}
