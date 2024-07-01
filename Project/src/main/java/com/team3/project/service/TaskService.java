package com.team3.project.service;

import com.team3.project.Classes.*;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskList;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAOService.*;
import com.team3.project.Websocket.WebsocketObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.team3.project.DAO.DAORole;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

// Interagiert mit Repository, also create, delete, get, set
@Service
public class TaskService {

    @Autowired
    private WebsocketObserver observer;

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     *
     * @param ID Identifier
     * @return Task-Object
     * @throws Exception Null Object
     */
    public Task getTaskByID(int ID) throws Exception{
        DAOTask dt =  DAOTaskService.getById(ID);
        if (dt == null){ throw new Exception("Task not found");}
        if (DAOTaskListService.getWithTasksById(dt.getId()) == null){
            return new Task(dt.getId(),dt.getDescription(), dt.getPriority(), dt.getUserStory().getId(), dt.getDueDate(), dt.getProcessingTimeEstimatedInHours(),dt.getProcessingTimeRealInHours(), -1);
        }
        return new Task(dt.getId(),dt.getDescription(), dt.getPriority() , dt.getUserStory().getId(),dt.getDueDate(), dt.getProcessingTimeEstimatedInHours(), dt.getProcessingTimeRealInHours(), DAOTaskListService.getWithTasksById(dt.getId()).getTaskBoard().getId());
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
        if (DAOTaskListService.getWithTasksById(dt.getId()) == null){
            return new Task(dt.getId(),dt.getDescription(), dt.getPriority(), dt.getUserStory().getId(), dt.getDueDate(), dt.getProcessingTimeEstimatedInHours(),dt.getProcessingTimeRealInHours(), -1);
        }
        return new Task(dt.getId(),dt.getDescription(), dt.getPriority(), dt.getUserStory().getId(), dt.getDueDate(), dt.getProcessingTimeEstimatedInHours(),dt.getProcessingTimeRealInHours(), DAOTaskListService.getWithTasksById(dt.getId()).getTaskBoard().getId());
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: Lucas Krüger
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     * save/create Task in Database
     * @param task Task Object
     * @throws Exception Null Params or Object not found
     */
    //TODO: integriere die verknüpften Nutzer
    public void saveTask(Task task) throws Exception{
        if (task == null) throw new Exception("Task not found");
        if (task.getDescription() == null ) throw new Exception("null description");
        if(task.getUserStoryID() <= -1) throw new Exception("invalid UserStory-ID");
        if(task.getID() == -1){
            boolean responce = false;
            if(task.getTbID() < 0){
                responce = DAOTaskService.create(task.getDescription(), task.getPriorityAsInt(),false,task.getDueDateAsString(),task.getTimeNeededG(),0,null, DAOUserStoryService.getById(task.getUserStoryID()), null);
            }else{
                responce = DAOTaskService.create(task.getDescription(), task.getPriorityAsInt(),false,task.getDueDateAsString(),task.getTimeNeededG(),0,DAOTaskListService.getByTaskBoardId(task.getTbID()).get(0), DAOUserStoryService.getById(task.getUserStoryID()), null);
            }
            if (responce) {
                try {
                    List<DAOTask> dtl = DAOTaskService.getAll();
                    DAOTask dt = dtl.get(dtl.size()-1);
                    int tbID = dt.getTaskList() == null ? -1 : dt.getTaskList().getTaskBoard().getId();
                    observer.sendToTaskGroup(0,
                            new Task(dt.getId(),dt.getDescription(),dt.getPriority(),dt.getUserStory().getId(),
                                    dt.getDueDate(),dt.getProcessingTimeEstimatedInHours(),
                                    dt.getProcessingTimeRealInHours(), tbID));
                } catch (Exception e) {
                    System.out.println("Observer nicht initialisiert");
                }
            }
        }else{
            DAOTask dt = DAOTaskService.getById(task.getID());
            if (dt != null){
                boolean responce = false;
                boolean responceTL = false;
                if(task.getTbID() > 0
                        && (dt.getTaskList() == null
                        || task.getTbID() != dt.getTaskList().getTaskBoard().getId())){
                    responceTL = responceTL || DAOTaskService.updateTaskBoardIdById(dt.getId(), task.getTbID());
                    responceTL = responceTL || DAOTaskService.updateProcessingTimeEstimatedInHoursById(task.getID(), 0);
                } else if (task.getTbID() == -1 && dt.getTaskList() != null){
                    responceTL = responceTL || DAOTaskService.updateTaskListById(dt.getId(),null);
                    responceTL = responceTL || DAOTaskService.updateProcessingTimeEstimatedInHoursById(task.getID(), 0);
                }
                if (!dt.getDescription().equals(task.getDescription()))
                    responce = responce || DAOTaskService.updateDescriptonById(task.getID(),task.getDescription());
                if (dt.getUserStory().getId() != task.getUserStoryID())
                    responce = responce || DAOTaskService.updateUserStoryById(task.getID(),task.getUserStoryID());
                if (dt.getPriority() != task.getPriorityAsInt())
                    responce = responce || DAOTaskService.updatePriorityById(task.getID(), task.getPriorityAsInt());
                if(!Objects.equals(dt.getDueDate(), task.getDueDateAsString()))
                    responce = responce || DAOTaskService.updateDueDateById(task.getID(), task.getDueDateAsString());
                if(dt.getProcessingTimeEstimatedInHours() != task.getTimeNeededG())
                    responce = responce || DAOTaskService.updateProcessingTimeEstimatedInHoursById(task.getID(), task.getTimeNeededG());
                if(dt.isDone()!= task.isDone()){
                    if (task.isDone()) responce = responce || DAOTaskService.setDoneById(task.getID());
                    else responce = responce || DAOTaskService.setUnDoneById(task.getID());
                }

                if (responce && !responceTL) {
                    try {
                        dt = DAOTaskService.getById(task.getID());
                        int tbID = dt.getTaskList() == null ? -1 : dt.getTaskList().getTaskBoard().getId();
                        observer.sendToTaskGroup(0,
                                new Task(dt.getId(),dt.getDescription(),dt.getPriority(),dt.getUserStory().getId(),
                                        dt.getDueDate(),dt.getProcessingTimeEstimatedInHours(),
                                        dt.getProcessingTimeRealInHours(), tbID));
                    } catch (Exception e) {
                        System.out.println("Observer nicht initialisiert");
                    }
                }
                else if (responceTL){
                    try {
                        dt = DAOTaskService.getById(task.getID());
                        int tbID = dt.getTaskList() == null ? -1 : dt.getTaskList().getTaskBoard().getId();
                        observer.sendToTaskListGroup(0,
                                new Task(dt.getId(),dt.getDescription(),dt.getPriority(),dt.getUserStory().getId(),
                                        dt.getDueDate(),dt.getProcessingTimeEstimatedInHours(),
                                        dt.getProcessingTimeRealInHours(), tbID));
                    } catch (Exception e) {
                        System.out.println("Observer nicht initialisiert");
                    }
                }
            } else throw new Exception("DB Task not found");
        }
    }


    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     * Delete Task in Database
     * @param taskID Identifier
     * @throws Exception Invalid taskID
     */
    public void deleteTask(int taskID) throws Exception{
        if (taskID == -1) throw new Exception("not valid TaskID");
        DAOTask dt = DAOTaskService.getById(taskID);
        if (dt == null) throw new Exception("Task not found");
        int tbID = dt.getTaskList() == null ? -1 : dt.getTaskList().getTaskBoard().getId();
        Task temp = new Task(dt.getId(),dt.getDescription(),dt.getPriority(),dt.getUserStory().getId(),
                dt.getDueDate(),dt.getProcessingTimeEstimatedInHours(),
                dt.getProcessingTimeRealInHours(), tbID);
        boolean responce = DAOTaskService.deleteById(taskID);
        if (responce) {
            try {
                observer.sendToTaskGroup(0, temp);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Observer nicht initialisiert");
            }
        }
    }



    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Erfragt alle Tasks aus der Datenbank
     * Grund: /
     * UserStory/Task-ID: T1.B1
     */
    /**
     * returns all Task in Database
     * @return null or LinkedList<Task>
     */
    public List<Task> getAllTask(){
        try {
            List<DAOTask> tasks = DAOTaskService.getAll();
            if (!tasks.isEmpty()) {
                List<Task> taskList = new LinkedList<>();
                for (DAOTask task : tasks) {
                    int tbID = task.getTaskList() == null ? -1 : task.getTaskList().getTaskBoard().getId();
                    Task toAdd = new Task(task.getId(),task.getDescription(), task.getPriority(), task.getUserStory().getId(), task.getDueDate(), task.getProcessingTimeEstimatedInHours(),task.getProcessingTimeRealInHours(),  tbID );
                    taskList.add(toAdd);
                }
                return taskList;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Erfragt alle Tasks unter einer UserStory aus der Datenbank
     * Grund: /
     * UserStory/Task-ID:
     */
    /**
     * returns all Tasks under a UserStoryID
     * @param usId Identifier
     * @return null or LinkedList<Task>
     * @throws Exception Null Params
     */
    public List<Task> getTasksbyUSID(int usId) throws Exception {
        if (usId == -1) throw new Exception("Null USID");
        List<DAOTask> tasks = DAOTaskService.getListByUserStoryId(usId);
        if (tasks.isEmpty()) return null;
        List<Task> list = new LinkedList<Task>();
        for (DAOTask task : tasks) {
            int tbID = task.getTaskList() == null ? -1 : task.getTaskList().getTaskBoard().getId();
            Task toAdd = new Task(task.getId(),task.getDescription(), task.getPriority(), task.getUserStory().getId(), task.getDueDate(), task.getProcessingTimeEstimatedInHours(), task.getProcessingTimeRealInHours(), tbID);
            list.add(toAdd);
        }
        return list;
    }

    public  void setTaskList(int tID, int tlID) throws Exception {
        if (tID <= 0) throw new Exception("Null TID");
        if (tlID < -1) throw new Exception("Null tlID");
        DAOTask dt = DAOTaskService.getById(tID);
        if (dt == null) throw new Exception("Task not Found");

        DAOTaskList list = null;
        if (tlID != 0) {
            list = DAOTaskListService.getById(tlID);
            if (list == null) throw new Exception("TaskList not Found");
        }

        if (list == null || list.getSequence() != 5)
            DAOTaskService.updateProcessingTimeRealInHoursById(dt.getId(), 0);

        boolean responce = false || DAOTaskService.updateTaskListById(dt.getId(), list);
        dt = DAOTaskService.getById(tID);
        if (responce) {
            try {
                int tbID = dt.getTaskList() == null ? -1 : dt.getTaskList().getTaskBoard().getId();
                observer.sendToTaskListGroup(3,
                        new Task(dt.getId(),dt.getDescription(),dt.getPriority(),dt.getUserStory().getId(),
                                dt.getDueDate(),dt.getProcessingTimeEstimatedInHours(),
                                dt.getProcessingTimeRealInHours(), tbID));
            } catch (Exception e) {
                System.out.println("Observer nicht initialisiert");
            }
        }
    }

    public void setRealTimeAufwand(int tID, double time) throws Exception {
        if (tID <= 0) throw new Exception("Invaid TID");
        if (time < 0) throw new Exception("Invalid Time");
        DAOTask dt = DAOTaskService.getById(tID);
        if (dt == null) throw new Exception("Task not Found");
        if(dt.getProcessingTimeRealInHours() != time) {
            boolean responce = DAOTaskService.updateProcessingTimeRealInHoursById(dt.getId(), time);
            if (responce) {
                try {
                    int tbID = dt.getTaskList() == null ? -1 : dt.getTaskList().getTaskBoard().getId();
                    observer.sendToTaskGroup(2,
                            new Task(dt.getId(),dt.getDescription(),dt.getPriority(),dt.getUserStory().getId(),
                                    dt.getDueDate(),dt.getProcessingTimeEstimatedInHours(),
                                    time, tbID));
                } catch (Exception e) {
                    System.out.println("Observer nicht initialisiert");
                }
            }
        }
    }

    public  List<Double> getAllAnforderungenG(){
        List<DAOTask> dtl = DAOTaskService.getAll();
        List<Double> AnforderungenG = new LinkedList<Double>();
        for (DAOTask dt : dtl){
            if (dt.getTaskList().getId() % 5 == 0){
                double g = dt.getProcessingTimeEstimatedInHours();
                AnforderungenG.add(g);
            }
        }
        return AnforderungenG;
    }

    public List<Double> getAllAnforderungenA(){
        List<DAOTask> dtl =DAOTaskService.getAll();
        List<Double> AnforderungenA = new LinkedList<Double>();
        for (DAOTask dt : dtl){
            if (dt.getTaskList().getId() % 5 == 0) {
                double g = dt.getProcessingTimeRealInHours();
                AnforderungenA.add(g);
            }
        }
        return AnforderungenA;
    }
    
    public List<String> getAllName(){
        List<String> name = new LinkedList<String>();
        List<DAOTask> dtl = DAOTaskService.getAll();
        for (DAOTask dt : dtl) {
            if (dt.getTaskList().getId() % 5 == 0){
                String toAdd = "'" + dt.getDescription() + "'";
                name.add(toAdd);
            }
        }
        return name;
    }

    public List<String> getAllNameByUSID(int usID) throws Exception {
        if (usID <= -1) throw new Exception("Null ID");
        List<String> name = new LinkedList<String>();
        for (Task t : getTasksbyUSID(usID)){
            String toAdd = "'"+t.getDescription()+"'";
            name.add(toAdd);
        }
        return name;
    }

    public void setUsers(int tid,List<Integer> uIDs) throws Exception {
        if (tid == -1) {
            List<DAOTask> dtl = DAOTaskService.getAll();
            tid = dtl.get(dtl.size()-1).getId();
        }
        List<DAOUser> dul = new LinkedList<DAOUser>();
        for (int id : uIDs){
            DAOUser du = DAOUserService.getById(id);
            dul.add(du);
        }
        boolean responce = DAOTaskService.updateUsersById(tid, dul);
        if (responce) {
            try {
                DAOTask dt = DAOTaskService.getWithUsersById(tid);
                List<User> ul = new LinkedList<User>();
                dt.getUsers().forEach(du ->{
                    User toAdd = new User(du.getName(),du.getId(), RoleService.toRoleList(du.getRoles()), du.getAuthorization().getAuthorization());
                    ul.add(toAdd);
                });
                observer.sendToTaskGroup(3,
                        new TaskXUser(dt.getId(), ul));
            } catch (Exception e) {
                System.out.println("Observer nicht initialisiert");
            }
        }
    }

    /* Author: Henry van Rooyen
     * Revisited: /
     * Funktion: aktualisiert die Bearbeitungszeit einer Task, sofern fertig
     * Grund: /
     * UserStory/Task-ID: T11.B2
     */
    public void editTaskTimeIfDone(final Task task,
                                   final double newProcessingTimeEstimatedInHours,
                                   final double newProcessingTimeRealInHours) throws Exception {
        if (task == null || newProcessingTimeEstimatedInHours < 0 || newProcessingTimeRealInHours < 0) {
            throw new IllegalArgumentException("Wrong input parameters");}
        if (task.isDone()){
            final DAOTask daoTask = DAOTaskService.getById(task.getID());
            DAOTaskService.updateProcessingTimeEstimatedInHoursById(daoTask.getId(), newProcessingTimeEstimatedInHours );
            DAOTaskService.updateProcessingTimeRealInHoursById(daoTask.getId(), newProcessingTimeRealInHours);
            task.setTimeNeededG(newProcessingTimeEstimatedInHours);
            task.setTimeNeededA(newProcessingTimeRealInHours);
        }
    }
}
