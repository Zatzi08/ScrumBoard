package com.team3.project.DAO;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="UserStorys")
public class DAOUserStory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="usID")
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "priority")
    private int priority;

    @OneToMany(mappedBy = "userStory", cascade = CascadeType.REMOVE)
    private List<DAOTask> tasks;


    public DAOUserStory() {}
    public DAOUserStory(String name, String description, int priority) {
        this.name = name;
        this.description = description;
        this.priority = priority;
    }
}
