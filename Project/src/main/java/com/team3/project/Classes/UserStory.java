package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Priority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStory {
    private int userSID;
    private String description;
    private Priority priority;

    public UserStory(int userSID, String description, Enumerations.Priority priority){
        this.userSID = userSID;
        this.description = description;
        this.priority = priority;
    }
}
