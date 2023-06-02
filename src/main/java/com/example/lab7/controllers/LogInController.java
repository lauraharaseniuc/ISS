package com.example.lab7.controllers;

import com.example.lab7.HelloApplication;
import com.example.lab7.exceptions.RepositoryException;
import com.example.lab7.exceptions.ServiceException;
import com.example.lab7.model.Client;
import com.example.lab7.model.Manager;
import com.example.lab7.model.Reservation;
import com.example.lab7.service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class LogInController {
    private Service service;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordTF;
    public void setService(Service service) {
        this.service=service;
    }

    private void initializeClientAttributes(Client client) {
        Set<Reservation> reservations = service.getClientReservations(client);
        client.setReservations(reservations);
    }

    private void openNoLogInWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("no-log-in-view.fxml"));
            Parent root = loader.load();
            NoLogInSessionController noLogInSessionController = loader.getController();
            noLogInSessionController.initialize(service);
            service.addObserver(noLogInSessionController);
            Scene logged_in_scene = new Scene(root);
            logged_in_scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("css_styles/client_session_styles.css")).toExternalForm());
            //Stage main_stage=(Stage)this.logInButton.getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(logged_in_scene);
            newStage.show();
        }catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void openClientWindow(Client client) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("user-main-view.fxml"));
            Parent root = loader.load();
            ClientSessionController sessionController = loader.getController();
            initializeClientAttributes(client);
            sessionController.initialize(service, client);
            service.addObserver(sessionController);
            Scene logged_in_scene = new Scene(root);
            logged_in_scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("css_styles/client_session_styles.css")).toExternalForm());
            //Stage main_stage=(Stage)this.logInButton.getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(logged_in_scene);
            newStage.show();
        }catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void openManagerWindow(Manager manager) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("manager-view.fxml"));
            Parent root = loader.load();
            ManagerSessionController managerSessionController = loader.getController();
            //initializeClientAttributes(client);
            managerSessionController.initialize(service, manager);
            service.addObserver(managerSessionController);
            Scene logged_in_scene = new Scene(root);
            //logged_in_scene.getStylesheets().add(Objects.requireNonNull(HelloApplication.class.getResource("css_styles/client_session_styles.css")).toExternalForm());
            //Stage main_stage=(Stage)this.logInButton.getScene().getWindow();

            Stage newStage = new Stage();
            newStage.setScene(logged_in_scene);
            newStage.show();
        }catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void onNoLogInChosen(ActionEvent actionEvent) {
        openNoLogInWindow();
    }

    public void onLogInPressed(ActionEvent actionEvent) {
        errorLabel.setText("");
        String username = usernameTF.getText();
        String password = passwordTF.getText();
        if (username.length()==0 || username==null) {
            errorLabel.setText("Introduceti username-ul!");
        }
        if (password.length()==0 || password==null) {
            errorLabel.setText("Introduceti parola!");
        }
        try {
            Object user = service.userLogIn(username, password);
            if (user instanceof Client) {
                openClientWindow((Client) user);
            }
            if (user instanceof Manager) {
                openManagerWindow((Manager) user);
            }
        } catch (ServiceException ex) {
            errorLabel.setText(ex.getMessage());
        }
    }
}
