package com.team3.project.Classes;

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

}
