package com.team3.project.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.team3.project.Classes.Email;
@Service
public class AccountService {
    // TODO: Delete Stuff, wenn DB da
    //Alternative um ohne DB zu schreiben
    public boolean checkMail(String Mail){
        return !Mail.equals("test@gmail.com");
    }
    public boolean login(String Email, String PW){
        return !Email.equals("Fail");
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: Übergabe der Userdaten an Datenbank
     * Grund: Speichern von Userdaten in Datenbank
     * UserStory/Task-ID: /
     */
    public boolean register(String username, String email, String passwort){

            //TODO:DB-Abfrage and check if already in DB
            //TODO: Methode für Anfrage an DB ein Account zu erstellen
            //if(check(Mail) == true){catch(Exception e){ throw new SecurityException("Deine E-Mail ist mit einem anderen Account verknüpft");}
            //else{createAccountDB(username, mail, passwort);}

            //provisorisch mit eigener check-Methode imitiert
        return !checkMail(email);
    }


    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: Neusetzen des Passworts eines Nutzers
     * Grund: /
     * UserStory/Task-ID: /
     */
    public void resetPasswort(String EMail, String Passwort){
        // TODO: implement resetPasswort für User
        //TODO: implementiere Funktion: E-Mail mit authorization-code (aus DB) an E-Mailadresse EMail schicken
    }
    /* Email um den Code zu schicken muss von Google geprüft werde, weil es geflaggt wurde
    public static void main(String[] Args){
        Email test = new Email();
        test.sendEmail("henryfreyschmidt2226@gmail.com");
    }*/
}
