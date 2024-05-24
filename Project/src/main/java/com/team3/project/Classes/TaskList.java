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
    private int sequence;

    public TaskList(int ID, String name, int sequence) {
        super(ID);
        this.name = name;
        this.tasks = new LinkedList<Task>();
        this.sequence = sequence;
    }

    public void setTasksInTaskList(List <DAOTask> daoTaskList)throws  Exception{
        if(daoTaskList == null) throw new Exception("Null List");
        if(daoTaskList.isEmpty()) throw new Exception("Empty List");
        List <Task> taskList = new LinkedList<Task>();
        daoTaskList.forEach(x->{
            try{
                taskList.add(new Task(x.getId(), x.getDescription(), x.getPriority(), x.getUserStory().getId(), x.getDueDate(), x.getProcessingTimeEstimatedInHours(), x.getProcessingTimeRealInHours(),x.getTaskList().getTaskBoard().getId()));
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        this.tasks = taskList;
    }
}
