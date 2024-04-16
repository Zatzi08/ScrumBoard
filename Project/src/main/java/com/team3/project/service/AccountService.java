package com.team3.project.service;

import com.team3.project.Classes.User;
import com.team3.project.DAOService.DAOAccountService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import com.team3.project.Classes.Email;
@Service
public class AccountService {
    public boolean checkMail(String Mail){
        return DAOAccountService.checkmail(Mail);
    }
    public boolean login(String email, String passwort) throws Exception {
        if( email == null) throw new Exception("Null Email");
        if( passwort == null) throw new Exception("Null Passwort");
        return DAOAccountService.LoginCheck(email,passwort);
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: Übergabe der Userdaten an Datenbank
     * Grund: Speichern von Userdaten in Datenbank
     * UserStory/Task-ID: /
     */
    public boolean register(String username, String email, String passwort) throws Exception {
        if( email == null) throw new Exception("Null Email");
        if( passwort == null) throw new Exception("Null Passwort");
        if( username == null) throw new Exception("Null Username");
        return DAOAccountService.createAccount(email,passwort);
    }


    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: Neusetzen des Passworts eines Nutzers
     * Grund: /
     * UserStory/Task-ID: /
     */
    public boolean resetPasswort(String email, String passwort) throws Exception {
        // TODO: Verification dass überhaupt erlaubt durch Email
        if( email == null) throw new Exception("Null Email");
        if( passwort == null) throw new Exception("Null Passwort");
        return DAOAccountService.updatePassword(email,passwort);
    }

    /* Email um den Code zu schicken muss von Google geprüft werde, weil es geflaggt wurde
    public static void main(String[] Args){
        Email test = new Email();
        test.sendEmail("henryfreyschmidt2226@gmail.com");
    }*/
}
