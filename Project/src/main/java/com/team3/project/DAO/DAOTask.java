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
    @Column(name = "tID")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    private int priority;
    
    @Column(name = "done")
    private boolean done = false;
    
    @Column(name = "doDate")
    private String doDate;

    @Column(name = "processingTimeEstimatedInHours")
    private double processingTimeEstimatedInHours;

    @Column(name = "processingTimeRealInHours")
    private double processingTimeRealInHours;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "taskListId")
    DAOTaskList taskList;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "userStoryID")
    DAOUserStory userStory;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
    public DAOTask(String description, int priority, boolean done, String doDate,
                   double processingTimeEstimatedInHours, double processingTimeRealInHours, 
                   DAOTaskList taskList, DAOUserStory userStory, List<DAOUser> users) {
        this.description = description;
        this.priority = priority;
        this.done = done;
        this.doDate = doDate;
        this.processingTimeEstimatedInHours = processingTimeEstimatedInHours;
        this.processingTimeRealInHours = processingTimeRealInHours;
        this.taskList = taskList;
        this.userStory = userStory;
        this.users = users;
    }


    public void cloneDAOTask(DAOTask task) {
        if (task.description != null) {
            this.description = task.description;
        }
        if (task.priority > 0) {
            this.priority = task.priority;
        }
        if (!task.done) {
            this.done = task.done;
        }
        if (task.doDate != null) {
            this.doDate = task.doDate;
        }
        if (task.processingTimeEstimatedInHours > 0) {
            this.processingTimeEstimatedInHours = task.processingTimeEstimatedInHours;
        }
        if (task.processingTimeRealInHours > 0) {
            this.processingTimeRealInHours = task.processingTimeRealInHours;
        }
        if (task.taskList != null) {
            this.taskList = task.taskList;
        }
        if (task.userStory != null) {
            this.userStory = task.userStory;
        }
        if (task.users != null) {
            this.users = task.users;
        }
    }
}