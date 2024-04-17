package com.team3.project.service;

import com.team3.project.Classes.Profile;
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
    public boolean login(String Email, String Passwort){
        return DAOAccountService.LoginCheck(Email,Passwort);
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: Übergabe der Userdaten an Datenbank
     * Grund: Speichern von Userdaten in Datenbank
     * UserStory/Task-ID: /
     */
    public boolean register(String username, String email, String passwort){
        return DAOAccountService.createAccount(email,passwort);
    }


    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: Neusetzen des Passworts eines Nutzers
     * Grund: /
     * UserStory/Task-ID: /
     */
    public boolean resetPasswort(String EMail, String Passwort){
        // TODO: Verification dass überhaupt erlaubt durch Email
        return DAOAccountService.updatePassword(EMail,Passwort);
    }
    /* Email um den Code zu schicken muss von Google geprüft werde, weil es geflaggt wurde
    public static void main(String[] Args){
        Email test = new Email();
        test.sendEmail("henryfreyschmidt2226@gmail.com");
    }*/
    public void createUser(User user){

    }
    public void addProfile(Profile p){


    }
    public void updateProfile(Profile p){

    }
}
