package com.example.lab7.service;

import com.example.lab7.exceptions.RepositoryException;
import com.example.lab7.exceptions.ServiceException;
import com.example.lab7.model.*;
import com.example.lab7.observer_pattern.*;
import com.example.lab7.repository.ClientRepositoryDb;
import com.example.lab7.repository.ManagerRepositoryDb;
import com.example.lab7.repository.PlayRepositoryDb;
import com.example.lab7.repository.ReservationRepositoryDb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Service implements Observable<Event> {
    private ClientRepositoryDb clientRepositoryDb;
    private ManagerRepositoryDb managerRepositoryDb;

    private PlayRepositoryDb playRepositoryDb;
    private ReservationRepositoryDb reservationRepositoryDb;
    private final List<Observer<Event>> observers= new ArrayList<>();

    public Service(ClientRepositoryDb clientRepositoryDb, ManagerRepositoryDb managerRepositoryDb, PlayRepositoryDb playRepositoryDb, ReservationRepositoryDb reservationRepositoryDb) {
        this.clientRepositoryDb = clientRepositoryDb;
        this.managerRepositoryDb = managerRepositoryDb;
        this.playRepositoryDb = playRepositoryDb;
        this.reservationRepositoryDb = reservationRepositoryDb;
    }

    @Override
    public void addObserver(Observer<Event> e) {
        this.observers.add(e);
    }

    @Override
    public void removeObserver(Observer<Event> e) {
        this.observers.remove(e);
    }

    @Override
    public void notifyObservers(Event event) {
        this.observers.forEach(obs->{obs.update(event);});
    }

    public Object userLogIn (String username, String password) throws RepositoryException {
        try {
            Client client = clientRepositoryDb.findByUsernameAndPassword(username, password);
            return client;
        } catch (RepositoryException ex) {

        }
        try {
            Manager manager = managerRepositoryDb.findByUsernameAndPassword(username, password);
            return manager;
        } catch (RepositoryException ex) {
            throw new ServiceException("Combinatie invalida de username si parola!");
        }
    }

    public List<Play> getPlaysInPeriod(LocalDate startDate, LocalDate endDate) {
        return playRepositoryDb.getPlaysWithRepresentationsInPeriod(startDate, endDate);
    }

    public List<Representation> getRepresentationsInPeriod(LocalDate startDate, LocalDate endDate, Play play) {
        return playRepositoryDb.getRepresentationsInPeriod(startDate.toString(), endDate.toString(), play);
    }

    public Reservation makeReservation(String firstName, String lastName, String phone, String email, Client client, Representation representation, int noOfSeats, List<String> seatIds) {
        Reservation reservation = new Reservation(noOfSeats, firstName, lastName, phone, email, representation, client);
        Reservation reservationAdded =  reservationRepositoryDb.addReservation(reservation, seatIds);
        this.notifyObservers(new ReservationEntityChangeEvent(ChangeEventType.RESERVATION_MADE, reservation));
        return reservationAdded;
    }

    public List<Reservation> getReservationsForRepresentation (Representation representation) {
        return reservationRepositoryDb.getReservationsForRepresentation(representation.getId());
    }

    public List<Seat> getReservedSeatsForReservation(Reservation reservation) {
        return reservationRepositoryDb.getSeatsForReservation(reservation.getId());
    }

    public Set<Reservation> getClientReservations(Client client) {
        return reservationRepositoryDb.getClientReservations(client.getId());
    }

    public void deleteReservation(Reservation reservation) {
        reservationRepositoryDb.deleteReservation(reservation.getId());
        this.notifyObservers(new ReservationEntityChangeEvent(ChangeEventType.RESERVATION_CANCELLED, reservation));
    }

    public List<Play> getAllPlays() {
        return playRepositoryDb.getPlays();
    }
    public void addPlay(String name, String description, String author) {
        playRepositoryDb.addPlay(name, description, author);
    }

    public void addRepresentation(String startTime, String date, Play play) {
        playRepositoryDb.addRepresentation(startTime, date, play.getId());
        this.notifyObservers(new RepresentationEntityChangeEvent(ChangeEventType.REPRESENTATION_ADDED, new Representation(startTime, date, play)));
    }
}
