package com.team3.project.Classes;

import com.team3.project.Classes.Account;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User extends abstraktDataClasses {
    private int ID;
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

    /* Author: Henry L. Freyschmidt
     * Funktion: Updated Userstory in Datenbank
     * Grund: /
     * UserStory/Task-ID: /
     */
    public boolean updateUser(String name, int userID, String privatDescription, String workDescription, Enumerations.Role roles, List<Integer> taskIDs){
        //TODO:implement checkUserID and updateUserDB
        //if check(userID)==false then throw expection "User not found"
        //else updateUserDB: Werte verändern (if Argument == null then Wert nicht ändern)
        //return updateUserDB(...);
        return true;
    }

}
