package com.team3.project.Classes;

import com.team3.project.DAO.DAOTask;
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
        daoTaskList.forEach(x->{
            try{
                taskList.add(new Task(x.getId(), x.getDescription(), x.getPriority(), x.getUserStory().getId(), x.getDueDate(), x.getProcessingTimeEstimatedInHours(), x.getProcessingTimeRealInHours(),-1));
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        this.tasks = taskList;
    }
}
