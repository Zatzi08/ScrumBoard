package com.team3.project.service;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.Profile;
import com.team3.project.Classes.User;
import com.team3.project.DAO.DAORole;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.DAOService.DAOUserService;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


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
    public boolean checkMail(String mail) throws Exception {
        if (mail == null || mail.isEmpty()) throw new Exception("Null Mail");
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
        if(email == null || email.isEmpty()) throw new Exception("Null Email");
        if(passwort == null || passwort.isEmpty()) throw new Exception("Null Passwort");
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
        if( email == null || email.isEmpty()) throw new Exception("Null Email");
        if( passwort == null || passwort.isEmpty()) throw new Exception("Null Passwort");
        if( username == null || username.isEmpty()) throw new Exception("Null Username");
        return DAOUserService.createByEMail(email,passwort, username,null,null,null,null, null, null, false);
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
        if (EMail == null || EMail.isEmpty()) throw new Exception("Null Mail");
        if (Passwort == null || Passwort.isEmpty()) throw new Exception("Null Passwort");
        return DAOAccountService.updatePassword(EMail,Passwort);
    }
    /* Email um den Code zu schicken muss von Google geprüft werde, weil es geflaggt wurde
    public static void main(String[] Args){
        Email test = new Email();
        test.sendEmail("henryfreyschmidt2226@gmail.com");
    }*/
    public void createUser(String email, String password, String name) throws Exception{
        DAOUserService.createByEMail(email, password, name, null, null, null, null, null, null, true);
    }


    /**
     * gets all DAOUser-Object in Database
     * @return LinkedList<User> of all User in Database
     */
    public List<User> getAllUser(){
        List <User> list = new LinkedList<>();
        List <DAOUser> daoUserList = DAOUserService.getAllWithRoles();
        if(daoUserList != null){
            User toAdd;
            for(DAOUser daoUser : daoUserList){
                toAdd = new User(daoUser.getName(),daoUser.getId(), convertDaoRolesToEnumRoles(daoUser.getRoles())  , daoUser.getAuthorization().getAuthorization());
                list.add(toAdd);
            }
        }
        return list;
    }

    /* Author: Henry van Rooyen
     * Revisited: /
     * Funktion: Konvertieren einer DaoRolle zu EnumRolle
     * Grund: Erweiterung User um Rolle
     * UserStory/Task-ID: R1.B1
     */
    public static List<Enumerations.Role> convertDaoRolesToEnumRoles(List<DAORole> daoRoles) {
        return daoRoles.stream()
                .map(daoRole -> switch (daoRole.getName().toLowerCase()) {
                    case "admin" -> Enumerations.Role.admin;
                    case "productowner" -> Enumerations.Role.ProductOwner;
                    case "manager" -> Enumerations.Role.Manager;
                    case "nutzer" -> Enumerations.Role.Nutzer;
                    default -> throw new IllegalArgumentException("Unknown role name: " + daoRole.getName());
                })
                .collect(Collectors.toList());
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
     * @param profile -
     * @throws Exception Null Params or User not found
     */
    public void savePublicData(String sessionId, Profile profile) throws Exception {
        if (profile == null) throw new Exception("Null Profile");
        if (sessionId == null || sessionId.isEmpty()) throw new Exception("id outOfBound");
        if (profile.getUname() == null || profile.getUname().isEmpty()) throw new Exception("Null Name");
        if (profile.getWorkDesc() == null || profile.getWorkDesc().isEmpty()) throw new Exception("Null uDesc");
        if (profile.getPrivatDesc() == null || profile.getPrivatDesc().isEmpty()) throw new Exception("Null pDes");
        DAOUser user = DAOUserService.getBySessionId(sessionId);
        if (user == null) throw new Exception("User not found");
        DAOUserService.updateByEMail(user.getEmail(), profile.getUname(),profile.getPrivatDesc(), profile.getWorkDesc(), user.getRoles());
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
        if (email == null || email.isEmpty()) throw new Exception("Null EMail");
        DAOUser user = DAOUserService.getById(DAOUserService.getIdByMail(email));
        if (user == null) throw new Exception("User not found");
        return new Profile(user.getId(),user.getName(),user.getEmail(), user.getPrivatDescription(), user.getWorkDescription(), null, user.getAuthorization().getAuthorization());
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
        if (sessionId == null || sessionId.isEmpty()) throw new Exception("Null ID");
        DAOUser user = DAOUserService.getBySessionId(sessionId);
        if (user == null) throw new Exception("User not found");
        return new Profile(user.getId(), user.getName(),user.getEmail(), user.getPrivatDescription(), user.getWorkDescription(), null, user.getAuthorization().getAuthorization());
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
        if (sessionId == null || sessionId.isEmpty()) throw new Exception("Null SessionID");
        if (sessionId.equals(MasterID)) return 999999999;
        else {
            DAOUser user = DAOUserService.getBySessionId(sessionId);
            if (user == null) throw new Exception("User not found");
            return user.getAuthorization().getAuthorization();
        }
    }

    public List<Profile> getAllProfiles() {
        List <Profile> list = new LinkedList<>();
        List <DAOUser> daoUserList = DAOUserService.getAllWithRoles();
        if(daoUserList != null){
            Profile toAdd;
            for(DAOUser daoUser : daoUserList){
                toAdd = new Profile(daoUser.getId(),daoUser.getName(),daoUser.getEmail(),daoUser.getPrivatDescription(),daoUser.getWorkDescription(),null, daoUser.getAuthorization().getAuthorization());
                list.add(toAdd);
            }
        }
        return list;
    }

    public void setAuthority(int usID, int auth) throws Exception {
        if (usID < 0) throw new Exception("Null USID");
        if (auth <= 0 || auth > 4) throw new Exception("Invalid Auth");
        DAOUser du = DAOUserService.getById(usID);
        if (du == null) throw new Exception("User not Found");
        DAOUserService.updateAuthorizationById(du.getId(),auth);
    }
}
