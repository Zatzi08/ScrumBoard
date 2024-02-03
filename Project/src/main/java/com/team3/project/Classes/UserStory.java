package com.team3.project.Classes;

import com.team3.project.Classes.Enumerations.Prioritys;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserStory {
    private int userID;
    private String description;
    private Prioritys priority;

    public UserStory(int userID, String description, Enumerations.Prioritys priority){
        this.userID = userID;
        this.description = description;
        this.priority = priority;
    }
}
