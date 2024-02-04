package com.team3.project.Classes;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Profile {
    private String name;
    private String privatDescription;
    private String workDescription;
    private Enumerations.Role roles;
    private List<Integer> taskIDs;

    public Profile(String name, String privatDescription, String workDescription, Enumerations.Role roles, List<Integer> taskIDs  ){
        this.name = name;
        this.privatDescription = privatDescription;
        this.workDescription = workDescription;
        this.roles = roles;
        this.taskIDs = taskIDs;
    }
}
