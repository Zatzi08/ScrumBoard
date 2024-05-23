package com.team3.project.service;

import com.team3.project.Classes.Task;
import com.team3.project.Classes.TaskList;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskList;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAOService.DAOTaskListService;
import com.team3.project.DAOService.DAOTaskService;
import com.team3.project.DAOService.DAOUserStoryService;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class TaskListService {

    //TODO: fix in daoTasks.add: baue verknüpfte Nutzer mit ein (momentan auf null)
    //TODO: DB-Funktion zum erstellen einer TaskListe fehlt
    public void saveTaskList(TaskList taskList)throws Exception{
        if(taskList.getID() < -1) throw new Exception("Invalid TaskListID");
        if(taskList.getID() == -1){
            //DAOTaskListService.createTaskList(taskList.getName());
        }else{
            if(taskList.getTasks() == null) throw new Exception("Empty TaskList");
            if(taskList.getName() == null) throw new Exception("Null Name");
            if(taskList.getName().isEmpty()) throw new Exception("Empty Name");
            List<DAOTask> daoTasks = null;
            taskList.getTasks().forEach(x->{
                daoTasks.add(new DAOTask(x.getDescription(),x.getPriorityAsInt(),x.isDone(), x.getDueDateAsString(), x.getTimeNeededG(), x.getTimeNeededA(), DAOTaskListService.getWithTasksById(x.getID()), DAOUserStoryService.getById(x.getID()), null));
            });
            DAOTaskListService.updateTasksById(taskList.getID(), daoTasks);
        }
    }

    public TaskList getTaskListByID(int tlID) throws Exception{
        if(tlID < 0) throw new Exception("Invalid TaskListID");
        DAOTaskList daoTaskList = DAOTaskListService.getById(tlID);
        if(daoTaskList == null) throw new Exception("TaskList not found");
        TaskList taskList = new TaskList(tlID,daoTaskList.getName() );
        try{
        taskList.setTasksInTaskList(daoTaskList.getTasks());
        }catch (Exception e){
            e.printStackTrace();
        }
        return taskList;
    }

    public List<TaskList> getAll() throws Exception{
        List<DAOTaskList> daoTaskLists= DAOTaskListService.getAll();
        if(daoTaskLists == null) throw new Exception("No TaskList existent");
        List<TaskList> taskLists = new LinkedList<TaskList>();
        daoTaskLists.forEach(x->{
            TaskList taskList = new TaskList(x.getId(),x.getName());
            try{
                taskList.setTasksInTaskList(x.getTasks());
            }catch (Exception e){
                e.printStackTrace();
            }
            taskLists.add(taskList);
        });
        return  taskLists;
    }

    public static TaskList toTaskList(DAOTaskList daotaskList) throws ParseException {
        TaskList taskList = new TaskList(daotaskList.getId(), daotaskList.getName());
        List<Task> tasks = new LinkedList<Task>();
        for (DAOTask daoTask : daotaskList.getTasks()) {
            Task toAdd = new Task(daoTask.getId(), daoTask.getDescription(), daoTask.getPriority(), daoTask.getUserStory().getId(), daoTask.getDueDate(), daoTask.getProcessingTimeEstimatedInHours(), daoTask.getProcessingTimeRealInHours(), -1);
            tasks.add(toAdd);
        }
        taskList.setTasks(tasks);
        return taskList;
    }
}
