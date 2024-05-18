package com.team3.project.Classes;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User extends abstraktDataClasses {
    private String name;
    private String privatDescription;
    private String workDescription;
    private Enumerations.Role roles;
    private List<Integer> taskIDs;
    private int authorization; //von der DB gestellt

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: User Konstruktor
     * Grund: /
     * UserStory/Task-ID: /
     */

    //Frage von Henry: soll beim Konstruktor von User die Profil-Sachen dabei sein?
    public User(String name, int userID, String privatDescription, String workDescription, Enumerations.Role roles){
        super(userID);
        this.name = name;
        this.privatDescription = privatDescription;
        this.workDescription = workDescription;
        this.roles = roles;
    }

}
