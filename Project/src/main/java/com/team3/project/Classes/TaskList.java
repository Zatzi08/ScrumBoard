package com.team3.project.Classes;

import java.util.ArrayList;
import java.util.List;

public class TaskList extends abstraktDataClasses {
    private String name;
    private List<Integer> taskIDs;

    public TaskList(int ID, String name) {
        super(ID);
        this.name = name;
        this.taskIDs = null;
    }
}
