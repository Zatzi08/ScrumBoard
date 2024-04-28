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
    
    @Column(name = "mail")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "privatDescription")
    private String privatDescription;
    
    @Column(name = "workDescription")
    private String workDescription;

    @Column(name = "authorization")
    private int authorization;

    @Column(name = "sessionID")
    private String sessionId;

    @Column(name = "sessionDate")
    private String sessionDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(
        name = "UsersXRoles", 
        joinColumns = @JoinColumn(name = "uid"), 
        inverseJoinColumns = @JoinColumn(name = "rid")    
    )
    private List<DAORole> roles;
    
    @ManyToMany(mappedBy = "users")
    private List<DAOTask> tasks;

    
    public DAOUser() {}
    public DAOUser(String name, String privatDescription, String workDescription, List<DAORole> roles) {
        this.name = name;
        this.privatDescription = privatDescription;
        this.workDescription = workDescription;
        this.roles = roles;
    }
    public DAOUser(String email, String name, String privatDescription, String workDescription, List<DAORole> roles) {
        this.email = email;
        this.name = name;
        this.privatDescription = privatDescription;
        this.workDescription = workDescription;
        this.roles = roles;
    }
    /*
    public DAOUser(String name, String privatDescription, String workDescription, int authorization, String sessionId) {
        this.name = name;
        this.privatDescription = privatDescription;
        this.workDescription = workDescription;
        this.authorization = authorization;
        this.sessionId = sessionId;
    }
    */

    public void cloneValues(DAOUser user) {
        if (user.getName() != null) {
            this.setName(user.getName());
        }
        if (user.getEmail() != null) {
            this.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            this.setPassword(user.getPassword());
        }
        if (user.getPrivatDescription() != null) {
            this.setPrivatDescription(user.getPrivatDescription());
        }
        if (user.getWorkDescription() != null) {
            this.setWorkDescription(user.getWorkDescription());
        }
        if (user.getAuthorization() != 0) {
            this.setAuthorization(user.getAuthorization());
        }
        if (user.getSessionId() != null) {
            this.setSessionId(user.getSessionId());
        }
        if (user.getSessionDate() != null) {
            this.setSessionDate(user.getSessionDate());
        }
        if (user.getRoles() != null) {
            this.setRoles(user.getRoles());
        }
        if (user.getTasks() != null) {
            this.setTasks(user.getTasks());
        }
    }
}
