package com.team3.project.DAO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String name;
    private String description;
    private int priority;


    public DAOUserStory(String name,String description,int priority){
        this.name=name;
        this.description=description;
        this.priority=priority;
    }
    public DAOUserStory(){}
    
}
