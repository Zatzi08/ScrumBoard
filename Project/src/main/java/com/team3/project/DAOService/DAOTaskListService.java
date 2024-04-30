package com.team3.project.DAOService;

import java.util.List;

import com.team3.project.DAO.DAOTaskList;

public class DAOTaskListService {
    //gets
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets all tasklists
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets all entries 
     * @return List of DAOTaskLists
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
     * @return   DAOTaskList
     */
    public static DAOTaskList getById(int id) {
        return DAOService.getByID(id, DAOTaskList.class);
    }

    
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets entry by ID with task
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets entry by ID with task
     * @param id identifier
     * @return   DAOTaskList
     */
    public static DAOTaskList getWithTasksById(int id) {
        String joinOnAttributeName = "tasks";
        return DAOService.getLeftJoinByID(id, DAOTaskList.class, joinOnAttributeName);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets entries by TaskBoardId
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets List of DAOTaskList by TaskBoardId
     * @param id identifier of taskboardid
     * @return   List of DAOTaskLists to the taskBoard
     */
    public static List<DAOTaskList> getByTaskBoardId(int id) {
        String parameterName = "taskBoard";
        return DAOService.getListByPara(DAOTaskList.class, id, parameterName);
    }
}
