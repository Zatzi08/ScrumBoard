package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Priority;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserStory extends abstraktDataClasses {
    private String name;
    private String description;
    private Priority priority;
    private List<Task> Tasks = null;

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public UserStory(String name, String description, int priority, int id){
        super(id);
        Enumerations prior = new Enumerations();
        this.name = name;
        this.description = description;
        this.priority = prior.IntToPriority(priority);
    }
}
