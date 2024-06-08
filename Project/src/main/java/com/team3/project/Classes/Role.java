package com.team3.project.Classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role extends abstraktDataClasses {
    private String name;

    public Role(int ID, String name) {
        super(ID);
        this.name = name;
    }
}
