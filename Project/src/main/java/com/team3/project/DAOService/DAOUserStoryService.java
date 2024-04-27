package com.team3.project.DAOService;

import java.util.List;

import com.team3.project.DAO.DAOUserStory;

public class DAOUserStoryService {
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: gets all entries for userStory
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** retrieves all userStories
     * @return list of all entries
     */
    public static List<DAOUserStory> getAll() {
        List<DAOUserStory> userStories = DAOService.getAll(DAOUserStory.class);
        return userStories;
    }
    
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: gets user story by ID
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** gets user story by ID
     * @param id identifier
     * @return
     */
    public static DAOUserStory getByID(int id) {
        DAOUserStory story = DAOService.getByID(id, DAOUserStory.class);
        return story;
    }

    
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets entry by ID
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets entry by ID 
     * @param id identifier
     * @return user as Object 
     */
    public static DAOUserStory getWithTasksById(int id) {
        return DAOService.getLeftJoinByID(id, DAOUserStory.class, "tasks");
    }

    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: gets userStory by name
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** gets userStory by name
     * @param name
     * @return
     */
    public static DAOUserStory getByName(String name) {
        String parameterName = "name";
        DAOUserStory story = DAOService.getSingleByPara(DAOUserStory.class, name, parameterName);
        return story;
    }

    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: creates a entry for a userStory
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** creates a entry for a userStory
     * @param name
     * @param description
     * @param priority
     * @return
     */
    public static boolean create(String name, String description, int priority) {
        if (!checkName(name)) {
            DAOUserStory userStory = new DAOUserStory(name, description, priority);
            try {
                DAOService.persist(userStory);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false; //name is already in database
    }

    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: removes a userStory from the database
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** removes a userStory from the database
     * @param id identifier
     * @return   true if deleted else false
     */
    public static boolean delete(int id) {
        DAOUserStory story = DAOService.getByID(id, DAOUserStory.class);
        if (story !=  null) {
            try {
                DAOService.delete(story);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: updates name by id
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** updates name by id
     * @param id   identifier
     * @param name new name
     * @return     true if updated else false
     */
    public static boolean updateName(int id, String name) {
        if (!checkName(name)) {
            DAOUserStory story = DAOService.getByID(id, DAOUserStory.class);
            if (story == null) {
                return false; //no entry
            }
            story.setName(name);
            try {
                DAOService.merge(story);
            } catch (Exception e) {
                return false;
            }
            return true;
        }
        return false; //name is already in database
    }

    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: updates a description by id
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** updates a description by id
     * @param id          identifier
     * @param description new Description
     * @return            true if updates else false
     */
    public static boolean updateDescription(int id, String description) {
        DAOUserStory story = DAOService.getByID(id, DAOUserStory.class);
        if (story == null) {
            return false; //no entry
        }
        story.setDescription(description);
        try {
            DAOService.merge(story);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: updates priority by id
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** updates priority by id
     * @param id       identifier 
     * @param priority new priority
     * @return         true if updates else false
     */
    public static boolean updatePriority(int id, int priority) {
        DAOUserStory story = DAOService.getByID(id, DAOUserStory.class);
        if (story == null) {
            return false; //no entry
        }
        story.setPriority(priority);
        try {
            DAOService.merge(story);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: checks if a userStory exists by id
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** checks if a userStory exists by id
     * @param id identifier
     * @return   true if userStory exists
     */
    public static boolean checkId(int id) {
        DAOUserStory story = DAOService.getByID(id, DAOUserStory.class);
        return (story != null) ? true : false;
    }
    
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: checks if a userStory exists by name
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** checks if a userStory exists by name
     * @param name name of userStory
     * @return   true if userStory exists
     */
     public static boolean checkName(String name) {
        String parameterName = "name";
        DAOUserStory story = DAOService.getSingleByPara(DAOUserStory.class, name, parameterName);
        return (story != null) ? true : false;
    }
}
