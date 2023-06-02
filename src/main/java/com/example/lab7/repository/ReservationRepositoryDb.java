package com.example.lab7.repository;

import com.example.lab7.exceptions.RepositoryException;
import com.example.lab7.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReservationRepositoryDb {
    private final SessionFactory sessionFactory;

    public ReservationRepositoryDb(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int addSeat(String zone, int row, int seatNo) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Seat seat = new Seat(zone, row, seatNo);
                int generatedId = (int)session.save(seat);
                tx.commit();
                return generatedId;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return -1;
    }

    private Seat getSeat(String zone, int row, int seatNo) {
        List<Seat> seatList = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Query query = session.createQuery("FROM Seat WHERE zone=:zone AND row=:row AND seatNo=:no");
                query.setParameter("zone",zone);
                query.setParameter("row",row);
                query.setParameter("no",seatNo);
                seatList = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        if (seatList.size() ==0) {
            throw new RepositoryException("No client with the given username and password was found!");
        }
        else {
            return seatList.get(0);
        }
    }

    private int addReservedSeat (ReservedSeat reservedSeat) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                int generatedId = (int)session.save(reservedSeat);
                tx.commit();
                return generatedId;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return -1;
    }

    private Set<ReservedSeat> addReservedSeats(Reservation reservation, List<String> seatIds) {
        Set<ReservedSeat> seats = new HashSet<>();
        seatIds.forEach(seatId -> {
            Seat referredSeat = getSeat(seatId.substring(0,1), Integer.parseInt(seatId.substring(1,2)), Integer.parseInt(seatId.substring(2,3)));
            ReservedSeat reservedSeat = new ReservedSeat(reservation, referredSeat);
            int generatedId = addReservedSeat(reservedSeat);
            reservedSeat.setId(generatedId);
            seats.add(reservedSeat);
        });
        return seats;
    }

    public Reservation addReservation(Reservation reservation, List<String> seatIds) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                int generatedId = (int)session.save(reservation);
                tx.commit();
                reservation.setId(generatedId);
                Set<ReservedSeat> reservedSeats = addReservedSeats(reservation, seatIds);
                reservation.setReservedSeats(reservedSeats);
                return reservation;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    public List<Reservation> getReservationsForRepresentation(int representation_id) {
        List<Reservation> reservations = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Query query = session.createQuery("FROM Reservation WHERE representation.id=:rep_id");
                query.setParameter("rep_id", representation_id);
                reservations = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return reservations;
    }

    public List<Seat> getSeatsForReservation(int reservation_id) {
        List<Seat> seats = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Query query = session.createQuery("FROM Seat WHERE id IN (SELECT referredSeat.id FROM ReservedSeat WHERE reservation.id=:res_id)");
                query.setParameter("res_id", reservation_id);
                seats = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return seats;
    }

    private Play getPlay(int play_id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Play play = session.get(Play.class, play_id);
                tx.commit();
                return play;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    private Representation getRepresentation (int representation_id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Representation representation = session.get(Representation.class, representation_id);
                tx.commit();
                Play play = this.getPlay(representation.getPlay().getId());
                representation.setPlay(play);
                return representation;
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return null;
    }

    public Set<Reservation> getClientReservations(int client_id) {
        List<Reservation> reservations = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Query query = session.createQuery("FROM Reservation WHERE client.id=:client_id");
                query.setParameter("client_id", client_id);
                reservations = query.list();
                tx.commit();
                reservations.forEach(reservation -> {
                    reservation.setRepresentation(this.getRepresentation(reservation.getRepresentation().getId()));

                    List<Seat> seats = getSeatsForReservation(reservation.getId());
                    Set<ReservedSeat> reservedSeats = new HashSet<>();
                    seats.forEach(seat -> {
                        reservedSeats.add(new ReservedSeat(reservation, seat));
                    });
                    reservation.setReservedSeats(reservedSeats);
                });
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        Set<Reservation> reservationSet = new HashSet<>();
        reservationSet.addAll(reservations);
        return reservationSet;
    }

    public void deleteReservation (int reservation_id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Reservation reservation = session.get(Reservation.class, reservation_id);

                Query query = session.createQuery("delete ReservedSeat where reservation=:reservation");
                query.setParameter("reservation", reservation);
                query.executeUpdate();

                session.delete(reservation);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
    }
}
