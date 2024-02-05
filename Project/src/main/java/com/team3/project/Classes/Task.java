package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Priority;
import lombok.Getter;
import lombok.Setter;


// Definiert Datentyp

@Getter
@Setter
public class Task {
    private int taskID;
    private String description;
    private Priority priority;
    public Task(){

    }

    public Task(int tID,String description, Priority priority){
        this.taskID = tID;
        this.description = description;
        this.priority = priority;
    }

}
