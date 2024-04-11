package com.team3.project.Classes;

import com.team3.project.Classes.Account;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User extends abstraktDataClasses {
    private Account account;
    private int ID;
    private String name;
    private String privatDescription;
    private String workDescription;
    private Enumerations.Role roles;
    private List<Integer> taskIDs;
    private int authorization; //von der DB gestellt

    public User(){}

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public User(Account account, String name, int userID, String privatDescription, String workDescription, Enumerations.Role roles, List<Integer> taskIDs){
        this.account = account;
        this.name = name;
        this.ID = userID;
        this.privatDescription = privatDescription;
        this.workDescription = workDescription;
        this.roles = roles;
        this.taskIDs = taskIDs;
    }

    /* Author: Henry L. Freyschmid
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
