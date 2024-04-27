package com.team3.project.DAOService;

import java.util.List;

import com.team3.project.DAO.DAOAccount;
import com.team3.project.DAO.DAORole;
import com.team3.project.DAO.DAOUser;

public class DAOUserService {
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets all users
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets all entries <p>
     * without 'fetch'
     * @return list of all users
     */
    public static List<DAOUser> getAll() {
        return DAOService.getAll(DAOUser.class);
    }

    public static List<DAOUser> getAllAndRoles() {
        String joinAttributeName = "roles";
        return DAOService.getAllLeftJoin(DAOUser.class, joinAttributeName);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets entry by ID
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets entry by ID <p>
     * without 'fetch'
     * @param id identifier
     * @return user as Object 
     */
    public static DAOUser getById(int id) {
        return DAOService.getByID(id, DAOUser.class);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets roles by sessionID
     * Reason:
     * UserStory/Task-ID:
     */
    /**
     * gets roles by sessionID
     * @param sessionID 
     * @return List of roles of the user
     */
    public static List<DAORole> getRolesBySessionID(int sessionID) {
        String query = "SELECT user FROM DAOUser AS user JOIN FETCH user.roles WHERE sessionId = ?1";
        DAOUser user = DAOService.getSingleByCustomQuery(DAOUser.class, query, Integer.toString(sessionID));
        return user.getRoles();
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets authorization by sessionID
     * Reason:
     * UserStory/Task-ID:
     */
    /**
     * gets authorization by sessionID
     * @param sessionID 
     * @return authorization int
     */
    public static int getAuthorizationBySessionID(int sessionID) {
        String parameterName = "sessionId";
        DAOUser user = DAOService.getSingleByPara(DAOUser.class, Integer.toString(sessionID), parameterName);
        return user.getAuthorization();
    }

    
    public static boolean create(String name, String email, String password, String sessionDate) {
        if (checkByEmail(email)) {
            return false; //email already in database
        }
        try {
            DAOService.persist(new DAOUser(name, email, password, sessionDate));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static boolean updateUsers(List<DAOUser> users) {
        return DAOService.mergeList(users);
    }

    /*
    public static boolean updateUserByMail(String email, DAOUser updatedUser) {
        String parameterName = "email";
        DAOUser user = DAOService.getSingleByPara(DAOUser.class, email, parameterName);
        if (user == null) {
            return false;
        }
        user.cloneValues(updatedUser);
        return DAOService.mergeB(user);
    }*/

    public static boolean checkByEmail(String email) {
        String parameterName = "email";
        DAOAccount account = DAOService.getSingleByPara(DAOAccount.class, email, parameterName);
        return (account != null) ? true : false;
    }
}