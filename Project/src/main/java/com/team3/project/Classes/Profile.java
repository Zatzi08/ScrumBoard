package com.team3.project.Classes;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter
@Setter
public class Profile extends dataClasses {
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
        return switch (this.authorization) {
            case 2 -> Enumerations.Role.Manager;
            case 3 -> Enumerations.Role.ProductOwner;
            case 4 -> Enumerations.Role.admin;
            default -> Enumerations.Role.Nutzer;
        };
    }

    public String toJSON() {
        String json = "{";
        json += "\"id\":\""+ this.getID();
        json += "\",\"name\":\""+ this.getUname();
        json += "\",\"email\":\""+ this.getEmail();
        json += "\",\"pDesc\":\""+ this.getPrivatDesc();
        json += "\",\"wDesc\":\""+ this.getWorkDesc();
        json += "\",\"auth\":\""+ this.getAuthorization();
        json += "\"}";
        return json;
    }
}
