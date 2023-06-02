package com.example.lab7;

import com.example.lab7.controllers.LogInController;
import com.example.lab7.model.Play;
import com.example.lab7.model.Reservation;
import com.example.lab7.repository.ClientRepositoryDb;
import com.example.lab7.repository.ManagerRepositoryDb;
import com.example.lab7.repository.PlayRepositoryDb;
import com.example.lab7.repository.ReservationRepositoryDb;
import com.example.lab7.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class HelloApplication extends Application {
    static SessionFactory sessionFactory;

    static void initializeSessionFactory() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void closeSessionFactory(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLogInWindowLoader= new FXMLLoader(HelloApplication.class.getResource("log-in-view.fxml"));
        Scene scene = new Scene(fxmlLogInWindowLoader.load(), 517, 281);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("css_styles/log_in_styles.css")).toExternalForm());

        stage.setTitle("Zorel Seat Reservation");
        stage.setScene(scene);
        stage.setResizable(false);
        LogInController logInController=fxmlLogInWindowLoader.getController();
        //stage.initStyle(StageStyle.UNDECORATED);

        try {
            initializeSessionFactory();
            ClientRepositoryDb clientRepositoryDb = new ClientRepositoryDb(sessionFactory);
            ManagerRepositoryDb managerRepositoryDb = new ManagerRepositoryDb(sessionFactory);
            PlayRepositoryDb playRepositoryDb = new PlayRepositoryDb(sessionFactory);
            ReservationRepositoryDb reservationRepositoryDb = new ReservationRepositoryDb(sessionFactory);
            Service service = new Service(clientRepositoryDb, managerRepositoryDb, playRepositoryDb, reservationRepositoryDb);
            logInController.setService(service);
        } catch (Exception ex) {
            closeSessionFactory();
        }


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}