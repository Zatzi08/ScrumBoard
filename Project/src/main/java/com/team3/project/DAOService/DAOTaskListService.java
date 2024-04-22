package com.team3.project.DAOService;

import java.util.List;

import com.team3.project.DAO.DAOTaskList;

public class DAOTaskListService {
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets all tasklists
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets all entries 
     * @return list of all tasks
     */
    public static List<DAOTaskList> getAll(){
        return DAOService.getAll(DAOTaskList.class);
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
    public static DAOTaskList getById(int id) {
        return DAOService.getByID(id, DAOTaskList.class);
    }
}
