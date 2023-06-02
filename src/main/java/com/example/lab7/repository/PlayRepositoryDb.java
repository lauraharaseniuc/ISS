package com.example.lab7.repository;

import com.example.lab7.exceptions.RepositoryException;
import com.example.lab7.model.Manager;
import com.example.lab7.model.Play;
import com.example.lab7.model.Representation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayRepositoryDb {
    private final SessionFactory sessionFactory;

    public PlayRepositoryDb(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int addPlay(String name, String description, String author) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Play play = new Play(name, description,author);
                session.save(play);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return -1;
    }

    public void addRepresentation(String startsAt, String date, int playId) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                Play play = session.load(Play.class, playId);
                Set<Representation> reps = play.getRepresentations();
                reps.add(new Representation(startsAt, date, play));
                play.setRepresentations(reps);

                //session.update(message);
                tx.commit();

            } catch(RuntimeException ex){
                System.err.println("Eroare la update "+ex);
                if (tx!=null)
                    tx.rollback();
            }
        }
    }

    public List<Play> getPlaysWithRepresentationsInPeriod (LocalDate startDate, LocalDate endDate) {
        String start = startDate.toString();
        String end = endDate.toString();
        List<Play> playList = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                String queryString = "from Play where id in (select distinct play.id from Representation where date > :startDate and date < :endDate)";
                Query query = session.createQuery(queryString);
                query.setParameter("startDate",start);
                query.setParameter("endDate",end);
                playList = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return playList;
    }

    public List<Play> getPlays() {
        List<Play> plays = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                String queryString = "from Play";
                Query query = session.createQuery(queryString);
                plays = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return plays;
    }

    public List<Representation> getRepresentationsInPeriod (String startDate, String endDate, Play play) {
        List<Representation> representationList = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                String queryString = "from Representation where play=:play and date> :startDate and date < :endDate";
                Query query = session.createQuery(queryString);
                query.setParameter("play",play);
                query.setParameter("startDate",startDate);
                query.setParameter("endDate",endDate);
                representationList = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return representationList;
    }
}
