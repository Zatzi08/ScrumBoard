package com.team3.project.DAO;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TaskLists")
public class DAOTaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tlID")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "sequence")
    private int sequence;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "taskBoardID")
    private DAOTaskBoard taskBoard;

    @OneToMany(mappedBy = "taskList", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<DAOTask> tasks;

    DAOTaskList() {}
    public DAOTaskList(String name, int sequence, DAOTaskBoard taskBoard, List<DAOTask> tasks) {
        this.name = name;
        this.sequence = sequence;
        this.taskBoard = taskBoard;
        this.tasks = tasks;
    }
}