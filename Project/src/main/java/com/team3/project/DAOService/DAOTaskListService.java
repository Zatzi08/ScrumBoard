package com.team3.project.DAOService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskBoard;
import com.team3.project.DAO.DAOTaskList;

import io.micrometer.common.lang.Nullable;

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
        String parameterName = "taskBoard.id";
        return DAOService.getListByPara(DAOTaskList.class, id, parameterName);
    }

    static boolean create(String name, int sequence, DAOTaskBoard taskBoard, @Nullable List<DAOTask> tasks) {
        //checkIfNameIsAlreadyListForTaskBoard
        DAOTaskList daoTaskList = new DAOTaskList(name, sequence, taskBoard, tasks);
        try {
            DAOService.merge(daoTaskList);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    static boolean createDefaultsForTaskBoardByTaskBoardName(String name) {
        List<String> defaultTaskLists = Arrays.asList("freie Tasks", "Tasks in Bearbeitung", "Tasks unter Review", "Tasks unter Test", "fertiggestellte Tasks");
        String joinOnAttributeName = "taskLists";
        DAOTaskBoard daoTaskBoard = DAOService.getLeftJoinByID(DAOTaskBoardService.getByName(name).getId(), DAOTaskBoard.class, joinOnAttributeName);
        for (String defaultTaskName : defaultTaskLists) {
            if (daoTaskBoard != null && daoTaskBoard.getTaskLists() != null && !daoTaskBoard.getTaskLists().stream().map(DAOTaskList::getName).collect(Collectors.toList()).contains(defaultTaskName)) {
                try {
                    create(defaultTaskName, defaultTaskLists.indexOf(defaultTaskName) + 1, daoTaskBoard, null);
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }



    public static boolean updateTasksById(int id, List<DAOTask> daoTasks) {
        String joinOnAttributeName = "tasks";
        DAOTaskList taskList = DAOService.getLeftJoinByID(id, DAOTaskList.class, joinOnAttributeName);
        taskList.setTasks(daoTasks);
        return DAOService.merge(taskList);
    }

    public static boolean addTaskById(int id, DAOTask daoTask) {
        String joinOnAttributeName = "tasks";
        DAOTaskList taskList = DAOService.getLeftJoinByID(id, DAOTaskList.class, joinOnAttributeName);
        taskList.getTasks().add(daoTask);
        return DAOService.merge(taskList);
    }

    
}
