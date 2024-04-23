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
    private int tlid;

    @Column(name = "name")
    private String name;

    @Column(name = "orders")
    private int orders;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "taskBoardID")
    private DAOTaskBoard taskBoard;

    @OneToMany(mappedBy = "Tasks")
    private List<DAOTask> tasks;

    DAOTaskList() {}
}
