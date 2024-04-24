package com.team3.project.DAOService;

import java.util.List;

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
    public static List<DAOUser> getAll(){
        return DAOService.getAll(DAOUser.class);
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
}