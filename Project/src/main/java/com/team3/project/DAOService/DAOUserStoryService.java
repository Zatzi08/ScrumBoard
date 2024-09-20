package com.team3.project.DAOService;

import java.util.List;

import com.team3.project.DAO.DAOUserStory;

public class DAOUserStoryService {
    //gets
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: gets all entries for userStory
     * Reason: refactoring
     * UserStory/Task-ID: U1.D1
     */
    /** retrieves all userStories
     * @return list of DAOUserStory
     */
    public static List<DAOUserStory> getAll() {
        List<DAOUserStory> userStories = DAOService.getAll(DAOUserStory.class);
        return userStories;
    }
    
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: gets user story by ID
     * Reason: refactoring
     * UserStory/Task-ID: U2.D1
     */
    /** gets user story by ID
     * @param id identifier
     * @return   DAOUserStory
     */
    public static DAOUserStory getById(int id) {
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
     * @return   DAOUserStory with List of DAOTasks
     */
    public static DAOUserStory getWithTasksById(int id) {
        String joinOnAttributeName = "tasks";
        return DAOService.getLeftJoinByID(id, DAOUserStory.class, joinOnAttributeName);
    }

    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: gets userStory by name
     * Reason: refactoring
     * UserStory/Task-ID:
     */
    /** gets userStory by name
     * @param name name
     * @return     DAOUserStory
     */
    public static DAOUserStory getByName(String name) {
        String parameterName = "name";
        DAOUserStory story = DAOService.getSingleByPara(DAOUserStory.class, name, parameterName);
        return story;
    }

    //creates
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: creates a entry for a userStory
     * Reason: refactoring
     * UserStory/Task-ID: U3.D2,U5.D2
     */
    /** creates a entry for a userStory
     * @param name        name
     * @param description description
     * @param priority    priority
     * @return            true if create is successfull
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

    //updates
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: updates name by id
     * Reason: refactoring
     * UserStory/Task-ID: U4.D1
     */
    /** updates name by id
     * @param id   identifier
     * @param name name
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
     * UserStory/Task-ID: U4.D1
     */
    /** updates a description by id
     * @param id          identifier
     * @param description description
     * @return            true if update is successfull
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
     * UserStory/Task-ID: U4.D1, U5.D3
     */
    /** updates priority by id
     * @param id       identifier 
     * @param priority priority
     * @return         true if update is successfull
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
    
    //deletes
    /* Author: Marvin Oliver Prüger
     * Revisited: Tom-Malte Seep
     * Function: removes a userStory from the database
     * Reason: refactoring
     * UserStory/Task-ID: U6.D1
     */
    /** removes a userStory from the database
     * @param id identifier
     * @return   true if delete is successfull
     */
    public static boolean deleteById(int id) {
        DAOUserStory userStory = DAOService.getByID(id, DAOUserStory.class);
        try {
            DAOService.delete(userStory);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //checks
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
     * @param name name
     * @return     true if userStory exists
     */
     public static boolean checkName(String name) {
        String parameterName = "name";
        DAOUserStory story = DAOService.getSingleByPara(DAOUserStory.class, name, parameterName);
        return (story != null) ? true : false;
    }
}
