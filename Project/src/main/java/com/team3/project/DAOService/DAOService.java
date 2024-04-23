package com.team3.project.DAOService;

import java.util.List;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;

class DAOService {
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: get all entries in a table
     * Reason:
     * UserStory/Task-ID: 
     */
    /** gets all the objects
     * @param <Dao>
     * @param daoClass the class that is to get
     * @return         list of all the entries in the table
     */
    static <Dao> List<Dao> getAll(Class<Dao> daoClass) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        String query = "SELECT item FROM " + daoClass.getName() + " AS item";
        List<Dao> retrieve;
        try {
            retrieve = entityManager.createQuery(query ,daoClass)
                .getResultList();
        } catch (Exception e) {
            retrieve = null;
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
        return retrieve;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: Get entry by ID
     * Reason:
     * UserStory/Task-ID:
     */
     /** gets a single object by ID
      * @param <Dao>
      * @param id       the integer id for the object
      * @param daoClass the class that is to get
      * @return         object of the daoclass type with the id
      */
    static <Dao> Dao getByID(int id, Class<Dao> daoClass) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        /* //old way of retriving the ObjectById
         * String query = "SELECT item FROM " + daoClass.getName() + " AS item where id = ?1";
         * Dao retrieve = session.createQuery(query, daoClass).setParameter(1, id).getSingleResult();
        */
        Dao retrieve;
        try {
            retrieve = entityManager.find(daoClass, id);
        } catch (Exception e) {
            retrieve = null;
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
        return retrieve;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: Get entry by a parameter
     * Reason:
     * UserStory/Task-ID:
     */
    /** get a single instance by the parameter
     * @param <Dao>
     * @param daoClass      the class that is to get
     * @param parameter     the value of the parameter that should be searched for
     * @param parameterName the parameter for the Query
     * @return              object that was searched for
     */
    static <Dao> Dao getSingleByPara(Class<Dao> daoClass, String parameter, String parameterName) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        String query = "SELECT item FROM " + daoClass.getName() + " AS item WHERE " + parameterName + " = ?1";
        Dao retrieve;
        try {
            retrieve = entityManager.createQuery(query, daoClass)
                .setParameter(1, parameter)
                .getSingleResult();
        } catch (Exception e) {
            retrieve = null;
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
        return retrieve;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: get entries by parameter
     * Reason:
     * UserStory/Task-ID:
     */
    /** get all the instaces by a parameter
     * @param <Dao>
     * @param daoClass      the class that is to get
     * @param parameter     the value of the parameter that should be searched for
     * @param parameterName the parameter for the Query
     * @return              list of objects that were searched for
     */
    static <Dao> List<Dao> getListByPara(Class<Dao> daoClass, String parameter, String parameterName) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        String query = "SELECT item FROM " + daoClass.getName() + " AS item WHERE " + parameterName + " = ?1";
        List<Dao> retrieve;
        try {
            retrieve = entityManager.createQuery(query, daoClass)
                .setParameter(1, parameter)
                .getResultList();
            return retrieve;
        } catch (Exception e) {
            retrieve = null;
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
        return retrieve;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: get entries by parameter
     * Reason:
     * UserStory/Task-ID:
     */
    /** get all the instaces by a parameter
     * @param <Dao>
     * @param daoClass      the class that is to get
     * @param parameter     the value of the parameter that should be searched for
     * @param parameterName the parameter for the Query
     * @return              list of objects that were searched for
     */
    static <Dao> List<Dao> getListByPara(Class<Dao> daoClass, int parameter, String parameterName) {
        return getListByPara(daoClass, Integer.toString(parameter), parameterName);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets the objects by a custom query
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets the objects by a custom query
     * @param <Dao>
     * @param daoClass  the searched class
     * @param query     query
     * @param parameter the parameter name
     * @return          list of Objects of daoClass type
     */
    static <Dao> List<Dao> getListByCustomQuery(Class<Dao> daoClass, String query, String parameter) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        List<Dao> retrieve;
        try {
            retrieve = entityManager.createQuery(query, daoClass)
                .setParameter(1, parameter)
                .getResultList();
        } catch (Exception e) {
            retrieve = null;
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
        return retrieve;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets a single object by a custom query
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets a single object by a custom query
     * @param <Dao>
     * @param daoClass  the searched class
     * @param query     query
     * @param parameter the parameter name
     * @return          object of daoClass type
     */
    static <Dao> Dao getSingleByCustomQuery(Class<Dao> daoClass, String query, String parameter) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        Dao retrieve;
        try {
            retrieve = entityManager.createQuery(query, daoClass)
                .setParameter(1, parameter)
                .getSingleResult();
        } catch (Exception e) {
            retrieve = null;
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
        return retrieve;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: persists the giving object in the database
     * Reason:
     * UserStory/Task-ID:
     */
    /** persists the given object in the database
     * @param daoObject the object to persist
     */
    static void persist(Object daoObject) {
        //TODO change the parameter type
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(daoObject);
            entityManager.getTransaction().commit();
        } catch (EntityExistsException e) {
            //
        } catch (Exception e) {
            //Something went wrong
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: merges the giving object in the database
     * Reason:
     * UserStory/Task-ID:
     */
    /** merge the given object in the database
     * @param daoObject the object to persist
     */
    static void merge(Object daoObject) {
        //TODO change the parameter type
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(daoObject);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            //Something went wrong
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: deletes a given object from the database
     * Reason:
     * UserStory/Task-ID:
     */
    /** deletes a given object from the database
     * @param daoObject
     */
    protected static void delete(Object daoObject) {
        //TODO change the parameter type
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(daoObject);
        } catch (Exception e) {
            //Something went wrong
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
    }
}
