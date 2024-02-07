package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Priority;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserStory {
    private int userID;
    private String name;
    private String description;
    private Priority priority;
    private List<Task> Tasks = null;
    private String id;

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public UserStory(int userID,String name, String description, Enumerations.Priority priority, String id){
        this.userID = userID;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.id = id;
    }
}
