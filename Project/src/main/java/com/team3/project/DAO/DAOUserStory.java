package com.team3.project.DAO;

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
@Table(name="Users")
public class DAOUserStory {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int usid;

    private String name;
    private String description;
    private int priority;
    private int id;


    public DAOUserStory(String name,String description,int priority,int id){
        this.name=name;
        this.description=description;
        this.priority=priority;
        this.id=id;
    }
    
}
