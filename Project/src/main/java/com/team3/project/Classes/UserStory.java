package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Priority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStory extends abstraktDataClasses {
    private String name;
    private String description;
    private Priority priority;

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public UserStory(String name, String description, int priority, int ID){
        super(ID);
        Enumerations prior = new Enumerations();
        this.name = name;
        this.description = description;
        this.priority = prior.IntToPriority(priority);
    }

    public int getPriorityAsInt() {
        Enumerations prior = new Enumerations();
        return prior.getInt(this.getPriority());
    }
}
