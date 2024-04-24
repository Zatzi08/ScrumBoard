package com.team3.project.service;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.Task;
import com.team3.project.DAO.DAORole;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.Classes.User;
import org.springframework.stereotype.Service;

import java.util.List;

// Interagiert mit Repository, also create, delete, get, set
@Service
public class TaskService {

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
    public void saveTask(int taskID, String description, int priority, int userID ) throws Exception{
        if (description == null | priority > 4 | priority < 0 | userID == -1){
            throw new Exception("invalid Arguments");
        }
        if(taskID == -1){
            //DAOTaskService.createTask(t);
        }else{
            /*if (DAOTaskService.getByID(task.getID()) != null){
                DAOTaskService.updateName(task.getID(),task.getName());
                DAOTaskService.updateDescription(task.getID(),task.getDescription());
                DAOTaskService.updatePriority(task.getID(), task.getPriority());
            }*/
            //TODO: DAOTaskService.updateName(ID,Name) fehlt
            //TODO: DAOTaskService.updateDescription(ID,Description) fehlt
            //TODO: DAOTaskService.updatePriority(ID,Priority) fehlt
        }
        //return createTaskDB(description,priority);
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
        //return DAOTaskService.getAll();
        return null; //TODO: DAOTaskService.getAll() fehlt
    }
}
