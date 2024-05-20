package com.team3.project.DAOService;

import java.util.Arrays;
import java.util.List;

import com.team3.project.DAO.DAOTaskBoard;

public class DAOTaskBoardService {
    //gets
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
     * @return   DAOTaskBoard 
     */
    public static DAOTaskBoard getById(int id) {
        return DAOService.getByID(id, DAOTaskBoard.class);
    }

    static DAOTaskBoard getByName(String name) {
        String parameterName = "name";
        return DAOService.getSingleByPara(null, name, parameterName);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets entry by ID
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets entry by ID 
     * @param id identifier
     * @return   DAOTaskBoard
     */
    public static DAOTaskBoard getWithTaskListsById(int id) {
        String joinOnAttributeName = "taskLists";
        return DAOService.getLeftJoinByID(id, DAOTaskBoard.class, joinOnAttributeName);
    }

    static DAOTaskBoard getWithTaskListsWithTasksById(int id) {
        List<String> joinOnAtrributeNames = Arrays.asList("tasklists", "tasklists.tasks");
        return null;
    }

    public static boolean createWithDefaultTaskLists(String name) {
        DAOTaskBoard daoTaskBoard = new DAOTaskBoard(name, null);
        try {
            DAOService.persist(daoTaskBoard);
            DAOTaskListService.createDefaultsForTaskBoardByTaskBoardName(name);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean updateNameById(int id, String name) {
        DAOTaskBoard taskBoard = DAOService.getByID(id, DAOTaskBoard.class);
        if (!checkNameExists(name)) {
            taskBoard.setName(name);
            return DAOService.merge(taskBoard);
        }
        return false;
    }

    public static boolean emptyTaskListsByTaskBoardId(int taskboardId) {
        DAOTaskBoard taskBoard = DAOTaskBoardService.getWithTaskListsById(taskboardId);
        taskBoard.setTaskLists(null);
        return DAOService.merge(taskBoard);
    }

    public static boolean emptyTaskListsByTaskBoard(DAOTaskBoard daoTaskBoard) {
        return emptyTaskListsByTaskBoardId(daoTaskBoard.getId());
    }

    public static boolean deleteById(int id) {
        DAOTaskBoard taskBoard = DAOService.getByID(id, DAOTaskBoard.class);
        if (taskBoard != null) {
            try { 
                DAOService.delete(taskBoard);
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.toString());
                return false;
            }
        }
        return true;
    }

    public static boolean checkNameExists(String name) {
        String parameterName = "name";
        DAOTaskBoard taskBoard = DAOService.getSingleByPara(DAOTaskBoard.class, name, parameterName);
        if (taskBoard != null) {
            return true;
        }
        return false;
    }
}
