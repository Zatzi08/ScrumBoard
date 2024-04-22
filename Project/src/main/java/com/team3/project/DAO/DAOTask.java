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

    @Column(name = "taskListId")
    private int taskListId;

    @Column(name = "userStoryId")
    private int userStoryId;

    DAOTask() {}
}
