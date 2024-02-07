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
    private int userStoryID;

    public Task(){

    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public Task(int tID, String description, Priority priority, int UserStoryID){
        this.taskID = tID;
        this.description = description;
        this.priority = priority;
        this.userStoryID = userStoryID;
    }

}
