package com.team3.project.service;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.Task;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAOService.DAOTaskService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

// Interagiert mit Repository, also create, delete, get, set
@Service
public class TaskService {

    private final Enumerations enumerations = new Enumerations();

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */

    public Task getTaskByID(int id) throws Exception{
        // Task t =  DAOTaskService.getTaskbyID(id);
        // if (t == null){ throw new Exception("Task not found");}
        // return t;
        //TODO: DAOTaskService.getTaskByID(ID) fehlt
        return null;
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */

    public Task getTaskByDescription(String description) throws Exception{
        // DAOTask t =  DAOTaskService.getTaskbyDescription(description);
        // if (t == null){ throw new Exception("Task not found");}
        // return t;
        return null;
        //TODO: Task suchen ohne die TaskID zu kennen
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public void saveTask(int taskID, String description, int priority, int USID ) throws Exception{
        if (description == null | priority > 4 | priority < 0 | USID == -1){
            throw new Exception("invalid Arguments");
        }
        if(taskID == -1){
            DAOTaskService.create(description,USID);
        }else{
            DAOTask task = DAOTaskService.getById(taskID);
            if (task != null){
                DAOTaskService.updateDescriptonById(taskID,description);
                DAOTaskService.updateUserStoryById(taskID,USID);
            }
        }
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public void deleteTask(int taskID) throws Exception{
        /*if(taskID == -1){
        throw new Exception("not valid TaskID");
        }else{
        *  DAOTaskService.deleteTask(taskID);
        *
         }*/
        //TODO: DAOUserService.getRoleBySessionID(sessionID) fehlt
        //TODO: DAOTaskService.deleteTask(taskID) fehlt
    }


    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Erfragt alle Tasks aus der Datenbank
     * Grund: /
     * UserStory/Task-ID: T1.B1
     */
    public List<Task> getAllTask(){
        List<DAOTask> tasks = DAOTaskService.getAll();
        if (tasks != null) {
            List<Task> taskList = new LinkedList<>();
            Enumerations prio = new Enumerations();
            for (DAOTask task : tasks) {
                Task toAdd = new Task(task.getTid(),task.getDescription(), prio.IntToPriority(task.getPriority()), task.getUserStory().getId());
                taskList.add(toAdd);
            }
            return taskList;
        }
        return null;
    }

    public List<Task> getTaskbyUSID(int usId) throws Exception {
        if (usId == -1) throw new Exception("Null USID");
        List<DAOTask> tasks = DAOTaskService.getListByUserStoryId(usId);
        if (tasks.isEmpty()) return null;
        List<Task> List = new LinkedList<Task>();
        for (DAOTask task : tasks) {
            Task toAdd = new Task(task.getTid(),task.getDescription(), enumerations.IntToPriority(task.getPriority()), usId);
            List.add(toAdd);
        }
        return List;
    }


}
