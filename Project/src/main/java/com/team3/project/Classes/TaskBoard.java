package com.team3.project.Classes;

import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskList;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class TaskBoard extends abstraktDataClasses {
    String name;
    List<TaskList> taskListList;


    public TaskBoard(int tbID, String name) {
        super(tbID);
        this.name = name;
        this.taskListList = new LinkedList<TaskList>();
    }

    public void setTaskListsInTaskBoard(List <DAOTaskList> daoTaskLists)throws  Exception{
        if(daoTaskLists == null) throw new Exception("Null List");
        if(daoTaskLists.isEmpty()) throw new Exception("Empty List");
        List <TaskList> taskLists = new LinkedList<TaskList>();
        for(DAOTaskList daoTaskList: daoTaskLists){
            try{
                TaskList taskList = new TaskList(daoTaskList.getId(), daoTaskList.getName());
                taskList.setTasksInTaskList(daoTaskList.getTasks());
                taskLists.add(taskList);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        this.taskListList = taskLists;
    }

}
