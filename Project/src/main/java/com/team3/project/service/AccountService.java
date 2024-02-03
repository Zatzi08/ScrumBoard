package com.team3.project.service;

import org.springframework.stereotype.Service;

@Service
public class AccountService {
    //Alternative um ohne DB zu schreiben
    public boolean found(String Mail){
        return !Mail.equals("test@gmail.com");
    }
    public boolean LoginCheck(String UName, String PW){
        return !UName.equals("Fail");
    }
    public boolean CreateAccount(String Username, String Mail, String passwort){
        //TODO: send Mail to DB-Layer and check if already in DB
        //provisorisch mit found imitiert
        return !found(Mail);
    }

    public void setPasswort(String EMail, String Passwort) {
        // TODO: implement setPasswort für User
    }
}
