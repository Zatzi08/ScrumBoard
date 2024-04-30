package com.team3.project.DAOService;

import java.util.List;

import com.team3.project.DAO.DAOAccount;
import com.team3.project.DAO.DAORole;
import com.team3.project.DAO.DAOUser;

import io.micrometer.common.lang.Nullable;

public class DAOUserService {
    //gets 
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

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static List<DAOUser> getAllPlusRoles() {
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
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static DAOUser getByIdPlusRoles(int id) {
        String joinAttributeName = "roles";
        return DAOService.getLeftJoinByID(id, DAOUser.class, joinAttributeName);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static DAOUser getBySessionId(int sessionId) {
        String parameterString = "sessionId";
        String joinOnAttributeName = "roles";
        DAOUser user = DAOService.getSingleLeftJoinByPara(null, parameterString, parameterString, joinOnAttributeName);
        return user;
    }

    /* Author: Tom-Malte Seep
     * Revisited: / 
     * Function: gets roles by sessionID
     * Reason: /
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
     * Revisited: Marvin Prüger
     * Function: gets id by mail
     * Reason: made getIdByMail public
     * UserStory/Task-ID:
     */
    public static int getIdByMail(String email) {
        String parameterName = "email";
        DAOUser user = DAOService.getSingleByPara(DAOUser.class, email, parameterName);
        return user.getUid();
    }

    //creates
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static boolean createByEMail(String email, String password, String name, String privatDescription, String workDescription, List<DAORole> roles) {
        return DAOService.persist(new DAOUser(email, password, name, privatDescription, workDescription, roles));
        //return createById(getIdByMail(email), name, privatDescription, workDescription, roles);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static boolean updateByEMail(String email, String name, String privatDescription, String workDescription, List<DAORole> roles) {
        return updateById(getIdByMail(email), name, privatDescription, workDescription, roles);
    }

    //updates
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static boolean updateById(int id, @Nullable String name, @Nullable String privatDescription, 
                                     @Nullable String workDescription, @Nullable List<DAORole> roles) {
        try {
            DAOUser user = DAOService.getByID(id, DAOUser.class);
            if (user != null) {
                user.cloneValues(new DAOUser(name, privatDescription, workDescription, roles));
                return DAOService.merge(user);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    static boolean updateById(int id, DAOUser user) {
        return updateById(id, user.getName(), user.getPrivatDescription(), user.getWorkDescription(), user.getRoles());
    }
    
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static boolean updateUsers(List<DAOUser> users) {
        return DAOService.mergeList(users);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static boolean updateSessionIdById(int id, String sessionId, String sessionDate) {
        DAOUser user = DAOService.getByID(id, DAOUser.class);
        if (user != null) {
            user.setSessionId(sessionId);
            user.setSessionDate(sessionDate);
            return DAOService.merge(user);
        }
        return false;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static boolean emptySessionIdById(int id) {
        return updateSessionIdById(id, null, null);
    }

    //deletes
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static boolean deleteById(int id) {
        DAOUser user = DAOService.getByID(id, DAOUser.class);
        if (user != null) {
            try { 
                DAOService.delete(user);
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.toString());
                return false;
            }
        }
        return true;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    static boolean deleteByMail(String email) {
        String parameterName = "email";
        DAOUser user = DAOService.getSingleByPara(DAOUser.class, email, parameterName);
        if (user != null) {
            try { 
                DAOService.delete(user);
            } catch (Exception e) {
                System.out.println("Error occurred: " + e.toString());
                return false;
            }
        }
        return true;
    }
    
    //checks
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static boolean checkByEmail(String email) {
        String parameterName = "email";
        DAOAccount account = DAOService.getSingleByPara(DAOAccount.class, email, parameterName);
        return (account != null) ? true : false;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: 
     * Reason:
     * UserStory/Task-ID:
     */
    public static boolean checkSessionId(int id) {
        try {
            String parameterName = "sessionId";
            DAOUser user = DAOService.getSingleByPara(DAOUser.class, id, parameterName);
            if (user == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}