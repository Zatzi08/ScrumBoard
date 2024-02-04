package com.team3.project.Classes;

import com.team3.project.Classes.Account;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class User {
    private Account account;
    private int userID;
    private String name;
    private String privatDescription;
    private String workDescription;
    private Enumerations.Role roles;
    private List<Integer> taskIDs;
    private int authorization; //von der DB gestellt

    public User(Account account, String name, int userID, String privatDescription, String workDescription, Enumerations.Role roles, List<Integer> taskIDs){
        this.account = account;
        this.name = name;
        this.userID = userID;
        this.privatDescription = privatDescription;
        this.workDescription = workDescription;
        this.roles = roles;
        this.taskIDs = taskIDs;
    }
    public boolean updateUser(String name, int userID, String privatDescription, String workDescription, Enumerations.Role roles, List<Integer> taskIDs){
        //TODO:implement checkUserID and updateUserDB
        //if check(userID)==false then throw expection "User not found"
        //else updateUserDB: Wferte verändern (if Argument == null then Wert nicht ändern)
        //return updateUserDB(...);
        return true;
    }

}
