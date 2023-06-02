package com.example.lab7;

import com.example.lab7.model.Client;
import com.example.lab7.repository.ClientRepositoryDb;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HibernateEntitiesMain {
    static SessionFactory sessionFactory;

    /**static void initializeSessionFactory() {
        // SessionFactory in Hibernate 5 example
        Configuration config = new Configuration();
        config.configure();
        // local SessionFactory bean created
        sessionFactory = config.buildSessionFactory();
    }*/
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
    public static void main(String[] args) {
        LocalDate today = LocalDate.parse("2019-03-29");
        System.out.println(today);
        /**try {
            initializeSessionFactory();
            ClientRepositoryDb clientRepositoryDb = new ClientRepositoryDb(sessionFactory);
            Client client =clientRepositoryDb.findByUsernameAndPassword("lauraa","1234");
            System.out.println(client.getId());
        } catch (Exception ex) {
            closeSessionFactory();
        }*/
    }
}
