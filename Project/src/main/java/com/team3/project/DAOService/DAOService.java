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
     * @param <Dao>    DAOObjects
     * @param daoClass DAOObject.class
     * @return         List of all the entries in the table
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
     * @param JoinOnAttributeName Attribute Name
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
      * @param <Dao>    DAOObjects
      * @param id       the integer id for the object
      * @param daoClass the class that is to get
      * @return         DAOObject with id
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
     * @param <Dao>    DAOObjects
     * @param id       identifier
     * @param daoClass DAOObject.class
     * @return         DAOObject with id and joined attribute
     */
    static <Dao> Dao getLeftJoinByID(int id, Class<Dao> daoClass, String joinOnAttributeName) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        String query = "SELECT item FROM " + daoClass.getName() + " AS item LEFT JOIN FETCH item." + joinOnAttributeName + " WHERE item.id = ?1";
        Dao retrieve;
        try {
            retrieve = entityManager.createQuery(query, daoClass)
                .setParameter(1, id)
                .getSingleResult();
        } catch (Exception e) {
            retrieve = null;
        } finally {
            DAOSession.closeEntityManager(entityManager);
        }
        return retrieve;
    }

    
    static <Dao> Dao getSingleLeftJoinsById(int id, Class<Dao> daoClass, List<String> joinOnAttributeNames) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        String query = "SELECT item FROM " + daoClass.getName() + " AS item";
        for (String joinOnAttributeName : joinOnAttributeNames) {
            query += " LEFT JOIN FETCH item." + joinOnAttributeName;
        }        
        query += " WHERE item.id = ?1";
        Dao retrieve;
        try {
            retrieve = entityManager.createQuery(query, daoClass)
                .setParameter(1, id)
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
     * Function: gets a single entry with a specific parameter
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets a single entry with a specific parameter
     * @param <Dao>               DAOObjects
     * @param daoClass            DAOObject.class
     * @param parameter           parameter value
     * @param parameterName       parameter name
     * @param joinOnAttributeName name of joined attribute
     * @return                    DAOObject
     */
    static <Dao> Dao getSingleLeftJoinByPara(Class<Dao> daoClass, String parameter, String parameterName, String joinOnAttributeName) {
        EntityManager entityManager = DAOSession.getNewEntityManager();
        entityManager.getTransaction().begin();
        String query = "SELECT item FROM " + daoClass.getName() + " AS item LEFT JOIN FETCH item." + joinOnAttributeName + " WHERE item." + parameterName +  " = ?1";
        Dao retrieve;
        try {
            retrieve = entityManager.createQuery(query, daoClass).setParameter(1, parameter).getSingleResult();
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
     * @param <Dao>         DAOObjects
     * @param daoClass      DAOObject.class
     * @param parameter     parameter value
     * @param parameterName parameter name
     * @return              DAOObject
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
     * Function: get a single instance by the parameter
     * Reason: overload the function
     * UserStory/Task-ID:
     */
    /** get a single instance by the parameter
     * @param <Dao>         DAOObjects
     * @param daoClass      DAOObject.class
     * @param parameter     parameter value
     * @param parameterName parameter name
     * @return              DAOObject
     */
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
     * @param <Dao>         DAOObjects
     * @param daoClass      DAOObject.class
     * @param parameter     parameter value
     * @param parameterName parameter name
     * @return              List of DAOObjects
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
     * Reason: overload the function
     * UserStory/Task-ID:
     */
    /** get all the instaces by a parameter
     * @param <Dao>         DAOObjects
     * @param daoClass      DAOObject.class
     * @param parameter     parameter value
     * @param parameterName parameter name
     * @return              List of DAOObjects
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
     * @param <Dao>     DAOObjects
     * @param daoClass  DAOObject.class
     * @param query     query
     * @param parameter parameter name
     * @return          List of DAOObjects
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
     * @param <Dao>     DAOObjects
     * @param daoClass  DAOObject.class
     * @param query     query
     * @param parameter parameter name
     * @return          DAOObject
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
    /** persists an Object
     * @param daoObject DAOObject to persist
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
     * Function: persists objects in the database
     * Reason:
     * UserStory/Task-ID:
     */ 
    /** persists a List of Object
     * @param <Dao>      DAOObjects
     * @param daoObjects List of DAOObjects
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

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: persists objects in the database and returns bool
     * Reason:
     * UserStory/Task-ID:
     */
    /** persists a List of Object
     * @param <Dao>      DAOObjects
     * @param daoObjects DAOObject.class
     * @return           true if persist is successfull
     */
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
     * Function: merge object in the database and returns bool
     * Reason:
     * UserStory/Task-ID:
     */
    /** merges object
     * @param daoObject DAOObject
     * @return          true if merge is successfull
     */
    static boolean merge(Object daoObject) {
        try {
            DAOService.merging(daoObject);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: merges a List of DAOObjects
     * Reason:
     * UserStory/Task-ID:
     */
    /** merges a List of DAOObjects
     * @param <Dao>      DAOObjects
     * @param daoObjects List of DAOObjects
     */
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

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: merges a List of DAOObjects and return a boolean
     * Reason:
     * UserStory/Task-ID:
     */
    /** merges a List of DAOObjects
     * @param <Dao>      DAOObjects
     * @param daoObjects List of DAOObjects
     * @return           true if mergeList is successfull
     */
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
     * Reason: wurde nicht commited 
     * UserStory/Task-ID:
     */
    /** deletes a given object from the database
     * @param daoObject
     */
    private static void deleting(Object daoObject) {
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

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: deletes a object and returns a boolean
     * Reason:
     * UserStory/Task-ID:
     */ 
    /** deletes a DAOObject
     * @param daoObject DAOObject
     * @return          true if delete was successfull
     */
    static boolean delete(Object daoObject) {
        try {
            deleting(daoObject);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: deletes a object by id and returns a boolean
     * Reason:
     * UserStory/Task-ID:
     */
    /** deletes a object by id
     * @param <Dao>    DAOObjects
     * @param id       identifier
     * @param daoClass DAOObjects.class
     * @return         true if delete is successfull
     */
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

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: deletes a List of objects
     * Reason:
     * UserStory/Task-ID:
     */
    /** deletes a List of objects
     * @param <Dao>      DAOObjects
     * @param daoClass   DAOObject.class
     * @param daoObjects List of DAOObjects
     */
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

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: deletes all entries to a DAOObject.class
     * Reason:
     * UserStory/Task-ID:
     */
    /** deletes all entries of a DAOObject.class
     * @param <Dao>    DAOObjects
     * @param daoClass DAOObject.class
     */
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
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: checks if an entry exists
     * Reason:
     * UserStory/Task-ID:
     */
    /** checks if an entry exists
     * @param <Dao>    DAOObjects
     * @param id       identifier
     * @param daoClass DAOObject.class
     * @return         true if DAOObject exists
     */
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
