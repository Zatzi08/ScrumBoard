package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Prioritys;
import lombok.Getter;
import lombok.Setter;


// Definiert Datentyp

@Getter
@Setter
public class Task {
    private int tID;
    private String description;
    private Prioritys priority;
    public Task(int tID,String description, Prioritys priority){
        this.tID = tID;
        this.description = description;
        this.priority = priority;
    }
}
