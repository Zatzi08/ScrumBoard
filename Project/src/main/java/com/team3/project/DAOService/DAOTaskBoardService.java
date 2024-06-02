package com.team3.project.DAOService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskBoard;
import com.team3.project.DAO.DAOTaskList;
import com.team3.project.DAO.DAOUserStory;

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

    public static boolean create(String name, List<DAOTaskList> daoTaskLists) {
        if (!existsName(name)) {
            if (daoTaskLists == null) {
                return createWithDefaultTaskLists(name);
            } 
            return createWithoutDefaultTaskLists(name, daoTaskLists);
        }
        return false;
    }

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

    public static boolean updateNameById(int id, String name) {
        DAOTaskBoard taskBoard = DAOService.getByID(id, DAOTaskBoard.class);
        if (!existsName(name)) {
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
                DAOTaskService.emptyTasksByTaskBoardId(id);
                DAOService.delete(taskBoard);
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.toString());
                return false;
            }
        }
        return true;
    }

    static boolean existsName(String name) {
        String parameterName = "name";
        DAOTaskBoard taskBoard = DAOService.getSingleByPara(DAOTaskBoard.class, name, parameterName);
        if (taskBoard != null) {
            return true;
        }
        return false;
    }
}
