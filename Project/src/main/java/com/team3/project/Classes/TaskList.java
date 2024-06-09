package com.team3.project.Classes;

import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskList;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class TaskList extends abstraktDataClasses {
    private String name;
    private List<Task> tasks;

    public TaskList(int ID, String name) {
        super(ID);
        this.name = name;
        this.tasks = new LinkedList<Task>();
    }

    public void setTasksInTaskList(List <DAOTask> daoTaskList)throws  Exception{
        if(daoTaskList == null) throw new Exception("Null List");
        if(daoTaskList.isEmpty()) throw new Exception("Empty List");
        List <Task> taskList = new LinkedList<Task>();
        for(DAOTask daoTask : daoTaskList){
            try{
                taskList.add(new Task(daoTask.getId(), daoTask.getDescription(), daoTask.getPriority(), daoTask.getUserStory().getId(), daoTask.getDueDate(), daoTask.getProcessingTimeEstimatedInHours(), daoTask.getProcessingTimeRealInHours(),daoTask.getTaskList().getTaskBoard().getId()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        this.tasks = taskList;
    }
}
