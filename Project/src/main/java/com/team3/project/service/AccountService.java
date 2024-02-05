package com.team3.project.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.team3.project.Classes.Email;
@Service
public class AccountService {
    //Alternative um ohne DB zu schreiben
    public boolean checkMail(String Mail){
        return !Mail.equals("test@gmail.com");
    }
    public boolean login(String Email, String PW){
        return !Email.equals("Fail");
    }
    public boolean register(String username, String email, String passwort){

            //TODO:DB-Abfrage and check if already in DB
            //TODO: Methode für Anfrage an DB ein Account zu erstellen
            //if(check(Mail) == true){catch(Exception e){ throw new SecurityException("Deine E-Mail ist mit einem anderen Account verknüpft");}
            //else{createAccountDB(username, mail, passwort);}

            //provisorisch mit eigener check-Methode imitiert
        return !checkMail(email);
    }
    public void authenticate(String email){

    }
    public void resetPasswort(String EMail, String Passwort){
        // TODO: implement setPasswort für User
        //TODO: implementiere Funktion: E-Mail mit authorization-code (aus DB) an E-Mailadresse EMail schicken
    }
    /* Email um den Code zu schicken muss von Google geprüft werde, weil es geflaggt wurde
    public static void main(String[] Args){
        Email test = new Email();
        test.sendEmail("henryfreyschmidt2226@gmail.com");
    }*/
}
