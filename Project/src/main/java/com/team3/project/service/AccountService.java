package com.team3.project.service;

import org.springframework.stereotype.Service;

@Service
public class AccountService {
    //Alternative um ohne DB zu schreiben
    public boolean check(String Mail){
        return !Mail.equals("test@gmail.com");
    }
    public boolean LoginCheck(String UName, String PW){
        return !UName.equals("Fail");
    }
    public boolean CreateAccount(String Username, String mail, String passwort){

            //TODO:DB-Abfrage and check if already in DB
            //TODO: Methode für Anfrage an DB ein Account zu erstellen
            //if(check(Mail) == true){catch(Exception e){ throw new SecurityException("Deine E-Mail ist mit einem anderen Account verknüpft");}
            //else{createAccountDB(username, mail, passwort);}

            //provisorisch mit eigener check-Methode imitiert
        return !check(mail);
    }

    public void setPasswort(String EMail, String Passwort) {
        // TODO: implement setPasswort für User
    }
}
