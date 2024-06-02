package com.team3.project.Classes;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User extends abstraktDataClasses {
    private String name;
    private List<Enumerations.Role> roles; // TODO: Roles (plural) sollte eigentlich ne Liste sein
    private int authorization; //von der DB gestellt

    /* Author: Henry L. Freyschmidt
     * Revisited: Henry van Rooyen 25.05.24
     * Funktion: User Konstruktor
     * Grund_ /
     * UserStory/Task-ID: /
     */

    public User(String name, int userID, List<Enumerations.Role> roles, int authorization){
        super(userID);
        this.name = name;
        this.roles = roles;
        this.authorization = authorization;
    }

}
