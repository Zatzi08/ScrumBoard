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
@Table(name="Roles")
public class DAORole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rID")
    private int id;

    @Column(name="name")
    private String name;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(
        name = "UsersXRoles", 
        joinColumns = @JoinColumn(name = "rid"),
        inverseJoinColumns = @JoinColumn(name = "uid")
    )
    private List<DAOUser> users;

    @ManyToMany()
    @JoinTable(
        name = "AuthorizationsXRoles", 
        joinColumns = @JoinColumn(name = "rid"),
        inverseJoinColumns = @JoinColumn(name = "aid")
    )
    private List<DAOAuthorization> authorizations;
    
    
    public DAORole() {}
    /** LEGACY CODE
     */
    public DAORole(String name) {
        this.name = name;
    }
    public DAORole(String name, List<DAOAuthorization> daoAuthorization) {
        this.name = name;
        this.authorizations = daoAuthorization;
    }
}
