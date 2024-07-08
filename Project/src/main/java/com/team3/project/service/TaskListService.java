package com.team3.project.service;

import com.team3.project.Classes.Task;
import com.team3.project.Classes.TaskList;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskList;
import com.team3.project.DAOService.DAOTaskListService;
import com.team3.project.DAOService.DAOUserStoryService;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class TaskListService {

    //TODO: fix in daoTasks.add: baue verknüpfte Nutzer mit ein (momentan auf null)
    //TODO: DB-Funktion zum erstellen einer TaskListe fehlt
    public void saveTaskList(TaskList taskList)throws Exception{
        if(taskList.getID() < 0) throw new Exception("Invalid TaskListID");
        if(taskList.getTasks() == null) throw new Exception("Empty TaskList");
        if(taskList.getName() == null) throw new Exception("Null Name");
        if(taskList.getName().isEmpty()) throw new Exception("Empty Name");
        List<DAOTask> daoTasks = null;
        for(Task task : taskList.getTasks()){
            daoTasks.add(new DAOTask(task.getDescription(),task.getPriorityAsInt(),task.isDone(), task.getDueDateAsString(), task.getTimeNeededG(), task.getTimeNeededA(), DAOTaskListService.getWithTasksById(task.getID()), DAOUserStoryService.getById(task.getID()), null));
        }
        DAOTaskListService.updateTasksById(taskList.getID(), daoTasks);

    }

    public TaskList getTaskListByID(int tlID) throws Exception{
        if(tlID < 0) throw new Exception("Invalid TaskListID");
        DAOTaskList daoTaskList = DAOTaskListService.getById(tlID);
        if(daoTaskList == null) throw new Exception("TaskList not found");
        TaskList taskList = null;
        try{
            taskList = toTaskList(daoTaskList);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return taskList;
    }

    public List<TaskList> getAll() throws Exception{
        List<DAOTaskList> daoTaskLists= DAOTaskListService.getAll();
        if(daoTaskLists == null) throw new Exception("No TaskList existent");
        List<TaskList> taskLists = new LinkedList<TaskList>();
        for(DAOTaskList daoTaskList : daoTaskLists){
            TaskList taskList = null;
            try{
                taskList = toTaskList(daoTaskList);
            }catch (ParseException e) {
                e.printStackTrace();
            }
            taskLists.add(taskList);
        }
        return  taskLists;
    }

    public static TaskList toTaskList(DAOTaskList daoTaskList) throws Exception {
        TaskList taskList = new TaskList(daoTaskList.getId(), daoTaskList.getName());
        daoTaskList = DAOTaskListService.getWithTasksById(daoTaskList.getId());
        if (daoTaskList == null) throw new Exception("Invalid TaskList");
        List<Task> tasks = new LinkedList<Task>();
        for (DAOTask daoTask : daoTaskList.getTasks()) {
            Task toAdd = new Task(daoTask.getId(), daoTask.getDescription(), daoTask.getPriority(), daoTask.getUserStory().getId(), daoTask.getDueDate(), daoTask.getProcessingTimeEstimatedInHours(), daoTask.getProcessingTimeRealInHours(), daoTaskList.getTaskBoard().getId());
            tasks.add(toAdd);
        }
        taskList.setTasks(tasks);
        return taskList;
    }
}
