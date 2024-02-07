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
@Table(name="Users")
public class DAOAccount {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int uid;

    @Column(name = "mail")
    private String email;

    private String password;

    public DAOAccount(){}
    public DAOAccount(String mail, String pw){
        this.email =mail;
        this.password = pw;
    }

}
