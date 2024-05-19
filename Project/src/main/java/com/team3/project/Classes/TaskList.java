package com.team3.project.Classes;

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
}
