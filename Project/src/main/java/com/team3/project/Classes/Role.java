package com.team3.project.Classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role extends dataClasses {
    private String name;
    private int auth;

    public Role(int ID, String name) {
        super(ID);
        this.name = name;
    }

    public String toJSON() {
        // TODO: implement toJSON
        System.out.println("Role toJSON muss noch implementiert werden");
        String json = "{";
        json += "}";
        return json;
    }
}
