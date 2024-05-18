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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name="Authorizations")
public class DAOAuthorization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aID")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="authorization")
    private int authorization;

    @OneToMany(mappedBy = "authorization")
    private List<DAOUser> users;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "AuthorizationsXRoles", 
        joinColumns = @JoinColumn(name = "aid"),
        inverseJoinColumns = @JoinColumn(name = "rid")
    )
    private List<DAORole> roles;
  
    
    public DAOAuthorization() {}
    /** DO NOT USE THIS
     * @param name
     * @param authorization
     */
    public DAOAuthorization(String name, int authorization) {
        this.name = name;
        this.authorization = authorization;
    }
}
