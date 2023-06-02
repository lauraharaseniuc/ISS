package com.example.lab7.repository;

import com.example.lab7.exceptions.RepositoryException;
import com.example.lab7.model.Client;
import com.example.lab7.model.Manager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ManagerRepositoryDb {
    private final SessionFactory sessionFactory;
    public ManagerRepositoryDb (SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }

    public int addManager(String name, String username, String password) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Manager manager = new Manager(name, username,password);
                session.save(manager);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return -1;
    }

    public Manager findByUsernameAndPassword(String username, String password) throws RepositoryException {
        List<Manager> managerList = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Query query = session.createQuery("FROM Manager WHERE username=:username AND password=:password");
                query.setParameter("username",username);
                query.setParameter("password",password);
                managerList = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        if (managerList.size() ==0) {
            throw new RepositoryException("No manager with the given username and password!");
        }
        else {
            return managerList.get(0);
        }
    }
}
