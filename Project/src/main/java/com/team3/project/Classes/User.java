package com.team3.project.Classes;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User extends dataClasses implements observable {
    private String name;
    private List<Role> roles; // TODO: Roles (plural) sollte eigentlich ne Liste sein
    private int authorization; //von der DB gestellt

    /* Author: Henry L. Freyschmidt
     * Revisited: Henry van Rooyen 25.05.24
     * Funktion: User Konstruktor
     * Grund_ /
     * UserStory/Task-ID: /
     */

    public User(String name, int userID, List<Role> roles, int authorization){
        super(userID);
        this.name = name;
        this.roles = roles;
        this.authorization = authorization;
    }

    public String toJSON() {
        String json = "{";
        json += "\"id\":\""+this.getID();
        json += "\",\"name\":\""+this.getName();
        json += "\"roles\":\""+ this.roleListAsJson();
        json += "\",\"authorization\":\""+this.getAuthorization();
        json += "\"}";
        return json;
    }

    private String roleListAsJson(){
        String json = "[";
        for (int i = 0; i < this.roles.size(); i++){
            json += this.roles.get(i).toJSON();
            if (i < this.roles.size()-1) json += ",";
        }
        json += "]";
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
