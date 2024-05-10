package com.team3.project.Classes;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Profile {
    private String uname;
    private String userdesc;
    private String description;
    public Profile(String name, String udesc, String desc){
        this.uname = name;
        this.userdesc = udesc;
        this.description = desc;
    }
}
