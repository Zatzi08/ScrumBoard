package com.team3.project.Classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role extends dataClasses implements observable{
    private String name;
    private Enumerations.Role auth;

    public Role(int ID, String name) {
        super(ID);
        this.name = name;
    }

    public String toJSON() {
        System.out.println("Role toJSON muss noch implementiert werden");
        String json = "{";
        json += "\"id\":\""+this.getID();
        json += "\",\"name\":\""+this.getName();
        json += "}";
        return json;
    }

    @Override
    public Integer getUSID_P() {
        return null;
    }

    @Override
    public Integer getTBID_P() {
        return null;
    }
}
