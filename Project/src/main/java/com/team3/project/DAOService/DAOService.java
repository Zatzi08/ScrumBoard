package com.team3.project.DAOService;

import java.util.List;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;

class DAOService {
    //gets
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
     * Function: gets all entries with the joined Attribute
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets all entries with the joined Attribute
     * @param <Dao>               DAOObjects
     * @param daoClass            DaoObject.class
     * @param joinOnAttributeName Attribute Name
     * @return                    List of returned Objects
     */
    static <Dao> List<Dao> getAllLeftJoin(Class<Dao> daoClass, String joinOnAttributeName) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        String query = "SELECT item FROM " + daoClass.getName() + " AS item LEFT JOIN FETCH item." + joinOnAttributeName;
        List<Dao> retrieve;
        try {
            retrieve = entityManager.createQuery(query, daoClass).getResultList();
        } catch (Exception e) {
            retrieve = null;
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
        return retrieve;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets all entries with the joined Attributes
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets all entries with the joined Attributes
     * @param <Dao>               DAOObjects
     * @param daoClass            DaoObject.class
     * @param joinOnAttributeName Attribute Name
     * @return                    List of returned Objects
     */
    static <Dao> List<Dao> getAllLeftJoin(Class<Dao> daoClass, List<String> JoinOnAttributeName) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        StringBuilder query = new StringBuilder("SELECT item FROM " + daoClass.getName() + " AS item");
        JoinOnAttributeName.stream().forEach(fetchName -> {
            query.append(" LEFT JOIN FETCH item." + fetchName);
        });
        List<Dao> retrieve;
        try {
            retrieve = entityManager.createQuery(query.toString(), daoClass).getResultList();
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
     * Function: Get entry by ID
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets entry with fetch by ID
     * @param <Dao>
     * @param id
     * @param daoClass
     * @return
     */
    static <Dao> Dao getLeftJoinByID(int id, Class<Dao> daoClass, String joinOnAttributeName) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        String query = "SELECT item FROM " + daoClass.getName() + " AS item LEFT JOIN FETCH item." + joinOnAttributeName + "WHERE id = ?1";
        Dao retrieve;
        try {
            retrieve = entityManager.createQuery(query, daoClass)
                .setParameter(0, id)
                .getSingleResult();
        } catch (Exception e) {
            retrieve = null;
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
        return retrieve;
    }

    static <Dao> Dao getSingleLeftJoinByPara(Class<Dao> daoClass, String parameter, String parameterName, String joinOnAttributeName) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        String query = "SELECT item FROM " + daoClass.getName() + " AS item LEFT JOIN FETCH item." + joinOnAttributeName + "WHERE " + parameterName +  " = ?1";
        Dao retrieve;
        try {
            retrieve = entityManager.createQuery(query, daoClass).setParameter(0, parameter).getSingleResult();
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

    static <Dao> Dao getSingleByPara(Class<Dao> daoClass, int parameter, String parameterName) {
        return getSingleByPara(daoClass, Integer.toString(parameter), parameterName);
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

    //creates
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: persists the giving object in the database
     * Reason:
     * UserStory/Task-ID:
     */
    /** persists the given object in the database
     * @param daoObject the object to persist
     */
    private static void persisting(Object daoObject) {
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
     * Function: persists object in the database and returns bool
     * Reason:
     * UserStory/Task-ID:
     */
    /** 
     * @param daoObject Object to persist
     * @return          true if persisting is successfull
     */
    static boolean persist(Object daoObject) {
        try {
            DAOService.persisting(daoObject);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: persists object in the database and returns bool
     * Reason:
     * UserStory/Task-ID:
     */ 
    private static <Dao> void persistingList(List<Dao> daoObjects) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        try {
            for (Dao daoObject : daoObjects) {
                entityManager.persist(daoObject);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            //Something went wrong
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
    }

    static <Dao> boolean persistList(List<Dao> daoObjects) {
        try {
            DAOService.persistingList(daoObjects);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //updates
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: merges the giving object in the database
     * Reason:
     * UserStory/Task-ID:
     */
    /** merge the given object in the database
     * @param daoObject the object to persist
     */
    private static void merging(Object daoObject) {
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

    static boolean merge(Object daoObject) {
        try {
            DAOService.merging(daoObject);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static <Dao> void mergingList(List<Dao> daoObjects) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        try {
            for (Dao daoObject : daoObjects) {
                entityManager.merge(daoObject);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            //Something went wrong
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
    }

    static <Dao> boolean mergeList(List<Dao> daoObjects) {
        try {
            mergingList(daoObjects);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //deletes
    /* Author: Tom-Malte Seep
     * Revisited: Marvin
     * Function: deletes a given object from the database
     * Reason: würde nicht commited 
     * UserStory/Task-ID:
     */
    /** deletes a given object from the database
     * @param daoObject
     */
    static void deleting(Object daoObject) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(daoObject);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            //Something went wrong
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
    }

    static boolean delete(Object daoObject) {
        try {
            deleting(daoObject);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static <Dao> boolean deleteById(int id, Class<Dao> daoClass) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        boolean returning = true;
        try {
            Dao daoObject = entityManager.find(daoClass, id);
            entityManager.remove(daoObject);
            entityManager.getTransaction().commit();
            returning = true;
        } catch (Exception e) {
        } finally {
            entityManager.close();
        }
        return returning;
    }

    static <Dao> void deleteList(Class<Dao> daoClass, List<Dao> daoObjects) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        try {
            for (Dao daoObject : daoObjects) {
                entityManager.remove(daoObject);
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            //Something went wrong
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
    }

    static <Dao> void deleteAll(Class<Dao> daoClass) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        try {
            String query = "SELECT item FROM " + daoClass.getName() + " AS item";
            List<Dao> retrieve;
            try {
                retrieve = entityManager.createQuery(query , daoClass)
                    .getResultList();
            } catch (Exception e) {
                retrieve = null;
            }
            if (retrieve != null) {
                for (Dao daoObject : retrieve) {
                    entityManager.remove(daoObject);
                }
            }
            entityManager.getTransaction().commit();
        } catch (Exception e) {

        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
    }

    //checks
    static <Dao> boolean checkExistsById(int id, Class<Dao> daoClass) {
        try {
            if (getByID(id, daoClass) == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
