package com.team3.project.service;

import com.team3.project.Classes.Profile;
import com.team3.project.Classes.User;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.DAOService.DAOUserService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;


@Service
public class AccountService {

    private final String MasterID = "EAIFPH8746531";

    /* Author: Henry Lewis Freyschmidt
     * Revisited: /
     * Funktion: Erfragt Existenz einer E-Mail in Datenbank
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     * check E-Mail in Database
     * @param mail -
     * @return Boolean - Existenz der E-Mail
     */
    public boolean checkMail(String mail){
        return DAOAccountService.checkMail(mail);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     * check E-Mail and Passwort in Database
     * @param email -
     * @param passwort -
     * @return Boolean - Parameter-pair is in Datenbank
     * @throws Exception Null Params
     */
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
    /**
     * create a new User in Database
     * @param email -
     * @param passwort -
     * @param username -
     * @return Boolean - successful operation
     * @throws Exception Null Params
     */
    public boolean register(String username, String email, String passwort) throws Exception {
        if( email == null) throw new Exception("Null Email");
        if( passwort == null) throw new Exception("Null Passwort");
        if( username == null) throw new Exception("Null Username");
        return DAOUserService.createByEMail(email,passwort, username,null,null,null, null, null, false);
    }


    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: Neusetzen des Passworts eines Nutzers
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     * updates Passwort in Database
     * @param EMail -
     * @param Passwort -
     * @return Boolean successful operation
     * @throws Exception Null Params
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
    public void createUser(String email, String password, String name) throws Exception{
        DAOUserService.createByEMail(email, password, name, null, null, null, null, null, true);
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
        */
    } // Für die Tests notwendig, da in Tests keine SessionID

    /**
     * gets all DAOUser-Object in Database
     * @return LinkedList<User> of all User in Database
     */
    public List<User> getAllUser(){
        List <User> list = new LinkedList<>();
        List <DAOUser> daoUserList = DAOUserService.getAll();
        if(daoUserList != null){
            User toAdd;
            for(DAOUser daoUser : daoUserList){
                toAdd = new User(daoUser.getName(),daoUser.getUid(),daoUser.getPrivatDescription(),daoUser.getWorkDescription(),null);
                list.add(toAdd);
            }
        }
        return list;
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     * saves non credential User-Data
     * @param sessionId -
     * @param name -
     * @param rolle -
     * @param wDesc workDescription = describes your job in the project
     * @param pDesc privatDescription = describes
     * @throws Exception Null Params or User not found
     */
    public void savePublicData(String sessionId, String name, String rolle, String wDesc, String pDesc) throws Exception {
        if (sessionId == null) throw new Exception("id outOfBound");
        if (name == null) throw new Exception("Null Name");
        if (rolle == null) throw new Exception("Null Rolle");
        if (wDesc == null) throw new Exception("Null uDesc");
        if (pDesc == null) throw new Exception("Null pDes");
        DAOUser user = DAOUserService.getBySessionId(sessionId);
        if (user == null) throw new Exception("User not found");
        DAOUserService.updateByEMail(user.getEmail(), name,pDesc, wDesc, user.getRoles());
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     *
     * @param email
     * @return
     * @throws Exception
     */
    public Profile getProfileByEmail(String email) throws Exception {
        if (email == null) throw new Exception("Null EMail");
        DAOUser user = DAOUserService.getById(DAOUserService.getIdByMail(email));
        if (user == null) throw new Exception("User not found");
        return new Profile(user.getName(), user.getPrivatDescription(), user.getWorkDescription(), null);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     *
     * @param sessionId
     * @return
     * @throws Exception
     */
    public Profile getProfileByID(String sessionId) throws Exception {
        if (sessionId == null) throw new Exception("Null ID");
        DAOUser user = DAOUserService.getBySessionId(sessionId);
        if (user == null) throw new Exception("User not found");

        return new Profile(user.getName(), user.getPrivatDescription(), user.getWorkDescription(), null);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     * gets the Authority-Level of a User out of the Database
     * @param sessionId -
     * @return int - Authority-Level
     * @throws Exception Null Params or Object not found
     */
    public int getAuthority(String sessionId) throws Exception {
        if (sessionId == null) throw new Exception("Null SessionID");
        if (sessionId.equals(MasterID)) return 999999999;
        else {
            DAOUser user = DAOUserService.getBySessionId(sessionId);
            if (user == null) throw new Exception("User not found");
            return user.getAuthorization();
        }
    }
}
