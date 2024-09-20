package com.team3.project.Classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
    private String mail;
    private String password;

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: Konstruktor Account
     * Grund: /
     * UserStory/Task-ID: A2.D1
     */
    public Account(String mail, String password){
        this.mail = mail;
        this.password = password;
    }
}
