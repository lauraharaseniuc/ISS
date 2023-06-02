package com.example.lab7.model;

import java.util.HashSet;
import java.util.Set;

public class Reservation {
    private int id;
    private int noOfSeats;
    private String firstName;
    private String lastName;
    private String phone;
    private String emailAddress;
    private Representation representation;
    private Client client;
    private Set<ReservedSeat> reservedSeats = new HashSet<>();

    public Reservation() {

    }

    public Reservation(int noOfSeats, String firstName, String lastName, String phone, String emailAddress, Representation representation, Client client) {
        this.noOfSeats = noOfSeats;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.emailAddress = emailAddress;
        this.representation = representation;
        this.client = client;
    }

    public Reservation(int id, int noOfSeats, String firstName, String lastName, String phone, String emailAddress) {
        this.id = id;
        this.noOfSeats = noOfSeats;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.emailAddress = emailAddress;
    }

    public Reservation(int noOfSeats, String firstName, String lastName, String phone, String emailAddress) {
        this.noOfSeats = noOfSeats;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.emailAddress = emailAddress;
    }

    public Reservation(int noOfSeats, String firstName, String lastName, String phone, String emailAddress, Representation representation, Client client, Set<ReservedSeat> reservedSeats) {
        this.noOfSeats = noOfSeats;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.emailAddress = emailAddress;
        this.representation = representation;
        this.client = client;
        this.reservedSeats = reservedSeats;
    }

    public Reservation(int id, int noOfSeats, String firstName, String lastName, String phone, String emailAddress, Representation representation, Client client, Set<ReservedSeat> reservedSeats) {
        this.id = id;
        this.noOfSeats = noOfSeats;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.emailAddress = emailAddress;
        this.representation = representation;
        this.client = client;
        this.reservedSeats = reservedSeats;
    }

    public Reservation(int noOfSeats, Representation representation, Client client) {
        this.noOfSeats = noOfSeats;
        this.representation = representation;
        this.client = client;
    }

    public Reservation(int id, int noOfSeats, Representation representation, Client client) {
        this.id = id;
        this.noOfSeats = noOfSeats;
        this.representation = representation;
        this.client = client;
    }

    public Reservation(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public Reservation(int id, int noOfSeats) {
        this.id = id;
        this.noOfSeats = noOfSeats;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Set<ReservedSeat> getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(Set<ReservedSeat> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public int getId() {
        return id;
    }

    public int getNoOfSeats() {
        return noOfSeats;
    }

    public Representation getRepresentation() {
        return representation;
    }

    public Client getClient() {
        return client;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNoOfSeats(int noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public void setRepresentation(Representation representation) {
        this.representation = representation;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
