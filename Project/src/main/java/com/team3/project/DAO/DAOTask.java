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
@Table(name = "Tasks")
public class DAOTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rID")
    private int tid;

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    private int priority;

    @Column(name = "doDate")
    private String doDate;

    @Column(name = "timeNeededG")
    private String timeNeededG;

    @Column(name = "timeNeededA")
    private String timeNeededA;

    /*
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "taskListId")
    DAOTaskList taskList;
    */
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "userStoryID")
    DAOUserStory userStory;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(
        name = "TasksXUsers", 
        joinColumns = @JoinColumn(name = "tid"), 
        inverseJoinColumns = @JoinColumn(name = "uid")    
    )
    private List<DAOUser> users;
    

    public DAOTask() {}
    public DAOTask(String description, /*DAOTaskList taskList,*/ DAOUserStory userStory) {
        this.description = description;
        /*this.taskList = taskList;*/
        this.userStory = userStory;
    }
}