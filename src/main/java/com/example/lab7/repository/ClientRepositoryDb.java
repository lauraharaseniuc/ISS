package com.example.lab7.repository;

import com.example.lab7.exceptions.RepositoryException;
import com.example.lab7.model.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryDb {
    private final SessionFactory sessionFactory;
    public ClientRepositoryDb (SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }

    public int addClient(String username, String password) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Client client = new Client(username,password);
                session.save(client);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return -1;
    }

    public Client findByUsernameAndPassword(String username, String password) throws RepositoryException {
        List<Client> clientList = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                Query query = session.createQuery("FROM Client WHERE username=:username AND password=:password");
                query.setParameter("username",username);
                query.setParameter("password",password);
                clientList = query.list();
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        if (clientList.size() ==0) {
            throw new RepositoryException("No client with the given username and password was found!");
        }
        else {
            return clientList.get(0);
        }
    }
}
