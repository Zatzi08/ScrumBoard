package com.team3.project.Classes;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
public class Profile extends abstraktDataClasses {
    private String uname;
    private String email;
    private String privatDesc;
    private String workDesc;
    private LinkedList<Role> roles;
    private int authorization; //von der DB gestellt

    public Profile(int id, String uname,String email, String privatDesc, String workDesc, LinkedList<Role> roles, int authorization){
        super(id);
        this.uname = uname;
        this.email = email;
        this.privatDesc = privatDesc;
        this.workDesc = workDesc;
        this.roles = roles;
        this.authorization = authorization;
    }

    public Enumerations.Role getAuthorizationAsEnum(){
        switch (this.authorization){
            case 2:
                return Enumerations.Role.Manager;
            case 3:
                return Enumerations.Role.ProductOwner;
            case 4:
                return Enumerations.Role.admin;
            default:
                return Enumerations.Role.Nutzer;
        }
    }
}
