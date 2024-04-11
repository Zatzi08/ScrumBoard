package com.team3.project.Classes;

import java.util.List;

public class TaskList extends abstraktDataClasses {
    private String name;
    private List<Integer> taskIDs;

    TaskList(int ID, String name, List<Integer> tasks){
        super(ID);
        this.name=name;
        this.taskIDs=tasks;
    }

}
