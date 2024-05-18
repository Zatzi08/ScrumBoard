package com.team3.project.DAOService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.team3.project.Classes.UserStory;
import com.team3.project.DAO.DAOAuthorization;
import com.team3.project.DAO.DAORole;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAO.DAOUserStory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

class DAO {
    private static EntityManagerFactory sessionFactory;
    public static void main(String[] args) {
        try {
        setUp();
        //  String TestEmail = "TestEmail";
        DAORoleService.deleteById(1);


        //Usertest();   
        tearDown();    
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static <T> List<T> getList(Class<T> daoClass, String fetchName) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        String query = "SELECT item FROM " + daoClass.getName() + " AS item JOIN FETCH item." + fetchName;
        List<T> retrieve;
        try {
            retrieve = entityManager.createQuery(query, daoClass).getResultList();
        } catch (Exception e) {
            retrieve = null;
        } finally {
            entityManager.close();
        }
        return retrieve;
    }

    private static int createSessionId() {
        Random random = new Random();
        return random.nextInt(10000000, 100000000);
    }

    protected static void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
              .configure("DB/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
              .build();
        try {
           sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
           // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
           // so destroy it manually.
           StandardServiceRegistryBuilder.destroy( registry );
           System.out.println(e);
           System.out.println("setup fehlgeschlagen 666666666666666666666666666666666666666666666666666");
        }
     } 
    protected static void tearDown() throws Exception {
        if ( sessionFactory != null ) {
           sessionFactory.close();
        }
    }
}
