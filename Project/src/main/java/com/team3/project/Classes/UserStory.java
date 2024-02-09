package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Priority;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserStory {
    private String name;
    private String description;
    private Priority priority;
    private List<Task> Tasks = null;
    // id darf nicht in der logic angepast werden nacht deswegen jetzt final -Marvin
    private final int id;

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public UserStory(String name, String description, Enumerations.Priority priority, int id){
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.id = id;
    }
}
