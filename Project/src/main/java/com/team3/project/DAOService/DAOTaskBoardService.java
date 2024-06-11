package com.team3.project.DAOService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.team3.project.DAO.DAOTaskBoard;
import com.team3.project.DAO.DAOTaskList;

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

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: 
     * Reason: 
     * UserStory/Task-ID:
     */
    public static DAOTaskBoard getByName(String name) {
        String parameterName = "name";
        return DAOService.getSingleByPara(DAOTaskBoard.class, name, parameterName);
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

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: gets taskboard with affiliated tasklists and affiliated tasks
     * Reason: 
     * UserStory/Task-ID: TB2.D1
     */
    public static DAOTaskBoard getWithTaskListsWithTasksById(int id) {
        List<String> joinOnAtrributeNames = Arrays.asList("taskLists", "taskLists.tasks");
        DAOTaskBoard daoTaskBoard = DAOService.getLeftJoinByID(id, DAOTaskBoard.class, joinOnAtrributeNames.get(0));
        List<DAOTaskList> daoTaskLists = new ArrayList<>();
        daoTaskBoard.getTaskLists().forEach(taskList -> {
            daoTaskLists.add(DAOTaskListService.getWithTasksById(taskList.getId()));
        });
        daoTaskBoard.setTaskLists(daoTaskLists);
        return daoTaskBoard;
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: creates a taskboard
     * Reason: 
     * UserStory/Task-ID: TB9.D3
     */
    /** Mit 
     * @param name TaskBoard Name
     * @param daoTaskLists TaskLists - Nullable for default Lists
     * @return returns true if create was successful
     */
    public static boolean create(String name, List<DAOTaskList> daoTaskLists) {
        if (!existsName(name)) {
            if (daoTaskLists == null) {
                return createWithDefaultTaskLists(name);
            } 
            return createWithoutDefaultTaskLists(name, daoTaskLists);
        }
        return false;
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: creates taskboard with custom tasklists
     * Reason: 
     * UserStory/Task-ID: TB9.D3
     */
    static boolean createWithoutDefaultTaskLists(String name, List<DAOTaskList> daoTaskLists) {
        DAOTaskBoard taskBoard = new DAOTaskBoard(name, daoTaskLists);
            try {
                DAOService.persist(taskBoard);
                DAOTaskBoard daoTaskBoard = DAOTaskBoardService.getByName(name);
                daoTaskLists.forEach(daoTaskList -> {
                    daoTaskList.setTaskBoard(daoTaskBoard);
                });
                DAOService.mergeList(daoTaskLists);
            } catch (Exception e) {
                return false;
            }
            return true;
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: creates taskboard with default tasklists
     * Reason: 
     * UserStory/Task-ID: TB9.D3
     */
    static boolean createWithDefaultTaskLists(String name) {
        DAOTaskBoard daoTaskBoard = new DAOTaskBoard(name, null);
        try {
            DAOService.persist(daoTaskBoard);
            boolean isCreatedWithDefaultTaskLists = DAOTaskListService.createDefaultsForTaskBoardByTaskBoardName(name);
            if (!isCreatedWithDefaultTaskLists) {
                DAOService.delete(getByName(name));
            }
            return isCreatedWithDefaultTaskLists;
        } catch (Exception e) {
        }
        return false;
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: updates name
     * Reason: 
     * UserStory/Task-ID: TB10.D1
     */
    public static boolean updateNameById(int id, String name) {
        DAOTaskBoard taskBoard = DAOService.getByID(id, DAOTaskBoard.class);
        if (!existsName(name)) {
            taskBoard.setName(name);
            return DAOService.merge(taskBoard);
        }
        return false;
    }

    // /* Author: Tom-Malte Seep
    //  * Revisited: 
    //  * Function: sets tasklists null
    //  * Reason: 
    //  * UserStory/Task-ID: TB11.D2
    //  */
    // public static boolean emptyTaskListsByTaskBoardId(int taskboardId) {
    //     DAOTaskBoard taskBoard = DAOTaskBoardService.getWithTaskListsById(taskboardId);
    //     taskBoard.setTaskLists(null);
    //     return DAOService.merge(taskBoard);
    // }

    // /* Author: Tom-Malte Seep
    //  * Revisited: 
    //  * Function: sets tasklists null 
    //  * Reason: 
    //  * UserStory/Task-ID: TB11.D2
    //  */
    // public static boolean emptyTaskListsByTaskBoard(DAOTaskBoard daoTaskBoard) {
    //     return emptyTaskListsByTaskBoardId(daoTaskBoard.getId());
    // }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: deletes a taskboard
     * Reason: 
     * UserStory/Task-ID: TB11.D1
     */
    public static boolean deleteById(int id) {
        DAOTaskBoard taskBoard = DAOService.getByID(id, DAOTaskBoard.class);
        if (taskBoard != null) {
            try { 
                DAOTaskService.emptyTasksByTaskBoardId(id);
                DAOService.delete(taskBoard);
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.toString());
                return false;
            }
        }
        return true;
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: checks if name exists
     * Reason: 
     * UserStory/Task-ID:
     */
    static boolean existsName(String name) {
        String parameterName = "name";
        DAOTaskBoard taskBoard = DAOService.getSingleByPara(DAOTaskBoard.class, name, parameterName);
        if (taskBoard != null) {
            return true;
        }
        return false;
    }
}
