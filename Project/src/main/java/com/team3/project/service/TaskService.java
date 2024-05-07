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
        DAOTask dt =  DAOTaskService.getById(id);
        if (dt == null){ throw new Exception("Task not found");}
        return new Task(dt.getTid(),dt.getDescription(), dt.getPriority() , dt.getUserStory().getId());
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */

    public Task getTaskByDescription(String description) throws Exception{
        DAOTask dt =  DAOTaskService.getByDescription(description);
        if (dt == null) throw new Exception("Task not found");
        return new Task(dt.getTid(),dt.getDescription(), dt.getPriority(), dt.getUserStory().getId());
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public void saveTask(int taskID, String description, int priority, int USID ) throws Exception{
        if (description == null ) throw new Exception("null description");
        if (priority < 0 || priority > 4) throw new Exception("wrong priority");
        if(USID == -1) throw new Exception("invalid UserStory-ID");
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
        if(taskID == -1) throw new Exception("not valid TaskID");
        DAOTaskService.deleteById(taskID);
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
                Task toAdd = new Task(task.getTid(),task.getDescription(), task.getPriority(), task.getUserStory().getId());
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
            Task toAdd = new Task(task.getTid(),task.getDescription(), task.getPriority(), usId);
            List.add(toAdd);
        }
        return List;
    }


}
