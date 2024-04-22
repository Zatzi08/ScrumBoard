package com.team3.project.DAOService;

import java.util.List;

import com.team3.project.DAO.DAOTaskBoard;

public class DAOTaskBoardService {
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets all tasks
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets all entries
     * @return list of all tasks
     */
    public static List<DAOTaskBoard> getAll(){
        return DAOService.getAll(DAOTaskBoard.class);
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
    public static DAOTaskBoard getById(int id) {
        return DAOService.getByID(id, DAOTaskBoard.class);
    }
}
