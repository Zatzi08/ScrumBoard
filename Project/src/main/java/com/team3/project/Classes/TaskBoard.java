package com.team3.project.Classes;

import lombok.Getter;
import lombok.Setter;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class TaskBoard extends dataClasses implements observable {
    String name;
    List<TaskList> taskListList;


    public TaskBoard(int tbID, String name) {
        super(tbID);
        this.name = name;
        this.taskListList = new LinkedList<TaskList>();
    }

    public String toJSON() {
        String json = "{";
        json += "\"id\":\""+ this.getID();
        json += "\",\"name\":\""+ this.getName();
        json += "\"}";
        return json;
    }

    @Override
    public Integer getUSID_P() {
        return null;
    }

    @Override
    public Integer getTBID_P() {
        return this.getID();
    }
}
