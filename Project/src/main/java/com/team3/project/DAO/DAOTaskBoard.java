package com.team3.project.DAO;

import java.util.List;

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
@Table(name = "TaskBoards")
public class DAOTaskBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tbID")
    private int tbid;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "taskBoard")
    private List<DAOTaskList> taskLists;

    
    DAOTaskBoard() {}
}
