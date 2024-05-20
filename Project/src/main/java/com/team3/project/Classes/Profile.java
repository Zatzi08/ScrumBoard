package com.team3.project.Classes;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
public class Profile {
    private String uname;
    private String email;
    private String privatDesc;
    private String workDesc;
    private LinkedList<Role> roles;

    public Profile(String uname,String email, String privatDesc, String workDesc, LinkedList<Role> roles){
        this.uname = uname;
        this.email = email;
        this.privatDesc = privatDesc;
        this.workDesc = workDesc;
        this.roles = roles;
    }
}
