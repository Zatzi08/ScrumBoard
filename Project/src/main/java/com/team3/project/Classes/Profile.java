package com.team3.project.Classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profile {
    private String name;
    private String privatDescription;
    private String workDescription;
    private Enumerations.Role roles;
    private int[] taskIDs;
    public Profile(String name, String privatDescription, String workDescription, Enumerations.Role roles, int[] taskIDs  ){
        this.name = name;
        this.privatDescription = privatDescription;
        this.workDescription = workDescription;
        this.roles = roles;
        this.taskIDs = taskIDs;
    }
}
