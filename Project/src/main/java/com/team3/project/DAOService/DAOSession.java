package com.team3.project.DAOService;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

final class DAOSession {
    private static EntityManagerFactory entityManagerFactory;

    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: returns the EntityManagerFactory
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** returns the EntityManagerFactory <p>
     * or starts it setup
     * @return 
     */
    private static EntityManagerFactory getEntityManagerFactory(){
        if (entityManagerFactory == null) {
            setUp();
        }
        return entityManagerFactory;
    }

    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: sets the EntityManagerFactory up
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** sets the EntityManagerFactory up
     */
    private static void setUp() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure("DB/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
            .build();
        try {
            entityManagerFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            String errorIdentifier = "setup fehlgeschlagen 666666666666666666666666666666666666666666666666666";
            System.out.println(e + "\n" + errorIdentifier);
        }
    }
    
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: tears down the EntityManagerFactory
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** tears down the EntityManagerFactory <p>
     * if one exists
     */
    static void tearDown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }

    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: creates new Entity Manager
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** creates new Entity Manager
     * @return
     */
    static EntityManager getNewEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: closes the EntityManager
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** closes the EntityManager
     * @param entityManager entityManager the given session
     */
    static void closeEntityManager(EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }
}