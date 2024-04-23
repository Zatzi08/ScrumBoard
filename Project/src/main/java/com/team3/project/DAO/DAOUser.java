package com.team3.project.DAO;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Users")
public class DAOUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    @Column(name="name")
    private String name;

    @Column(name="privatDescription")
    private String privatDescription;
    
    @Column(name="workDescription")
    private String workDescription;

    @Column(name="authorization")
    private int authorization;

    @Column(name = "sessionID")
    private String sessionId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
        name = "UsersXRoles", 
        joinColumns = @JoinColumn(name = "uid"), 
        inverseJoinColumns = @JoinColumn(name = "rid")    
    )
    private List<DAORole> roles;
   

    public DAOUser(){}
    /* public DAOUser(String name, String privatDescription, String workDescription, int authorization, String sessionId, List<DAORole> roles){
        this.name = name;
        this.privatDescription = privatDescription;
        this.workDescription = workDescription;
        this.authorization = authorization;
        this.sessionId = sessionId;
        this.roles = roles;
    } */
}
