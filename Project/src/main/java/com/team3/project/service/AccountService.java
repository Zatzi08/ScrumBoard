package com.team3.project.service;

import com.team3.project.Classes.Profile;
import com.team3.project.Classes.User;
import com.team3.project.DAOService.DAOAccountService;
import org.springframework.stereotype.Service;


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

    public void SavePublicData(int id, String name, String rolle, String uDesc, String pDesc) throws Exception {
        if (id == -1) throw new Exception("id outOfBound");
        if (name == null) throw new Exception("Null Name");
        if (rolle == null) throw new Exception("Null Rolle");
        if (uDesc == null) throw new Exception("Null uDesc");
        if (pDesc == null) throw new Exception("Null pDes");
        //DAOAccountService.updatePublicData(id,name,rolle,uDesc,pDesc);

    }

    public Profile getProfileByName(String name) throws Exception {
        if (name == null) throw new Exception("Null Name");
        return null;//DAOAccountService.getAccountByName(name); TODO: Implement, wenn DB bereit
    }

    public Profile getProfileByID(int id) throws Exception {
        if (id == -1) throw new Exception("Null ID");
        return null; //DAOAccountService.getAccountByID(id); TODO: Implement, wenn DB bereit
    }
}
