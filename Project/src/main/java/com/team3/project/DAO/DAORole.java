package com.team3.project.DAO;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private int rid;

    @Column(name="name")
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<DAOUser> users;
    
    
    public DAORole() {}
    public DAORole(String name) {
        this.name = name;
    }
}
