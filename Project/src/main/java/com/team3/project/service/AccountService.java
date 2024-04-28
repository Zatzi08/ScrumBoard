package com.team3.project.service;

import com.team3.project.Classes.Profile;
import com.team3.project.Classes.User;
import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.DAOService.DAOUserService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


@Service
public class AccountService {
    public boolean checkMail(String Mail){
        return DAOAccountService.checkMail(Mail);
    }
    public boolean login(String email, String passwort) throws Exception {
        if( email == null) throw new Exception("Null Email");
        if( passwort == null) throw new Exception("Null Passwort");
        return DAOAccountService.checkLogin(email,passwort);
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
        return DAOAccountService.create(email,passwort);
    }


    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: Neusetzen des Passworts eines Nutzers
     * Grund: /
     * UserStory/Task-ID: /
     */
    public boolean resetPasswort(String EMail, String Passwort) throws Exception {
        if (EMail == null) throw new Exception("Null Mail");
        if (Passwort == null) throw new Exception("Null Passwort");
        return DAOAccountService.updatePassword(EMail,Passwort);
    }
    /* Email um den Code zu schicken muss von Google geprüft werde, weil es geflaggt wurde
    public static void main(String[] Args){
        Email test = new Email();
        test.sendEmail("henryfreyschmidt2226@gmail.com");
    }*/
    public void createUser(User user) throws Exception{

    }
    public void deleteProfile(String sessionID) throws Exception{
        /* user =  DAOUserService.getUserBySessionID(sessionID);
        if(user.getDescription() == null){
        throw new Exception("Profil existiert nicht");
         }else{
         DAOUserService.updatePublicData(user.getID(),null,null,null,null);
         }
        */
    }
    public void testDeleteProfile(int userID){
        /*
        * DAOUserService.updateUname(null,userID);
        * DAOUserService.updateUserDescription(null, userID);
        * DAOUserService.updateDescription(null, userID);
        * */
    } // Für die Tests notwendig, da in Tests keine SessionID

    public List<User> getAllUser(){
        List <User> list = new LinkedList<>();
        //List <User> list = DAOUserService.getAllUser();
        return list;
        //TODO: DAOUserService.getAllUser() fehlt
    }

    public void savePublicData(String SessionId, String name, String rolle, String uDesc, String pDesc) throws Exception {
        if (SessionId == null) throw new Exception("id outOfBound");
        if (name == null) throw new Exception("Null Name");
        if (rolle == null) throw new Exception("Null Rolle");
        if (uDesc == null) throw new Exception("Null uDesc");
        if (pDesc == null) throw new Exception("Null pDes");
        //int id = DAOUserService.getUserBySession(SessionId).getID(); TODO: Implement, wenn DB bereit
        //DAOAccountService.updatePublicData(id,name,rolle,uDesc,pDesc);

    }

    public Profile getProfileByName(String name) throws Exception {
        if (name == null) throw new Exception("Null Name");
        return null;//DAOAccountService.getAccountByName(name); TODO: Implement, wenn DB bereit
    }

    public Profile getProfileByID(String SessionId) throws Exception {
        if (SessionId == null) throw new Exception("Null ID");
        return null;//DAOUserService.getUserBySessionID(SessionId); TODO: Implement, wenn DB bereit
    }
}
