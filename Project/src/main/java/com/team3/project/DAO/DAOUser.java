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
import jakarta.persistence.ManyToOne;
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
    @Column(name="uID")
    private int id;

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

    @Column(name = "sessionID")
    private String sessionId;

    @Column(name = "sessionDate")
    private String sessionDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "UsersXRoles", 
        joinColumns = @JoinColumn(name = "uid"),
        inverseJoinColumns = @JoinColumn(name = "rid")
    )
    private List<DAORole> roles;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "TasksXUsers", 
        joinColumns = @JoinColumn(name = "uid"), 
        inverseJoinColumns = @JoinColumn(name = "tid")
    )
    private List<DAOTask> tasks;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "authorization_id")
    private DAOAuthorization authorization;
    
    
    public DAOUser() {}
    /** DO NOT USE
     * @param name
     * @param privatDescription
     * @param workDescription
     * @param roles
     */
    public DAOUser(String name, String privatDescription, String workDescription, List<DAORole> roles, DAOAuthorization authorization) {
        this.name = name;
        this.privatDescription = privatDescription;
        this.workDescription = workDescription;
        this.roles = roles;
        this.authorization = authorization;
    }
    /** DO NOT USE
     * @param name
     * @param privatDescription
     * @param workDescription
     * @param roles
     * @param sessionId
     * @param sessionDate
     */
    public DAOUser(String name, String privatDescription, String workDescription, List<DAORole> roles, DAOAuthorization authorization,
                   String sessionId, String sessionDate) {
        this.name = name;
        this.privatDescription = privatDescription;
        this.workDescription = workDescription;
        this.roles = roles;
        this.authorization = authorization;
        this.sessionId = sessionId;
        this.sessionDate = sessionDate;
    }
    public DAOUser(String email, String password, String name, String privatDescription, String workDescription, List<DAORole> roles,
                   DAOAuthorization daoAuthorization) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.privatDescription = privatDescription;
        this.workDescription = workDescription;
        this.roles = roles;
        this.authorization = daoAuthorization;
    }
    public DAOUser(String email, String password, String name, String privatDescription, String workDescription, List<DAORole> roles,
                   String sessionId, String sessionDate, DAOAuthorization daoAuthorization) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.privatDescription = privatDescription;
        this.workDescription = workDescription;
        this.roles = roles;
        this.sessionId = sessionId;
        this.sessionDate = sessionDate;
        this.authorization = daoAuthorization;
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

    public void cloneDAOUser(DAOUser user) {
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
        if (user.getAuthorization() != null) {
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
