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

    public Profile(String name,String email, String udesc, String wdesc, LinkedList<Role> roles){
        this.uname = name;
        this.email = email;
        this.privatDesc = udesc;
        this.workDesc = wdesc;
        this.roles = roles;
    }
}
