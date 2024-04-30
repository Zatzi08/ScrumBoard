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
     * @return List of DAOUser
     */
    public static List<DAOUser> getAll() {
        return DAOService.getAll(DAOUser.class);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets all entries
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets all DAOUser
     * @return List of DAOUser with filled roles
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
     * @return   DAOUser
     */
    public static DAOUser getById(int id) {
        return DAOService.getByID(id, DAOUser.class);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets entry by id
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets entry by id
     * @param id identifier
     * @return   DAOUser with filled roles
     */
    public static DAOUser getByIdPlusRoles(int id) {
        String joinAttributeName = "roles";
        return DAOService.getLeftJoinByID(id, DAOUser.class, joinAttributeName);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets entry by sessionId
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets entry by sessionId
     * @param sessionId sessionId
     * @return          DAOUser
     */
    public static DAOUser getBySessionId(String sessionId) {
        String parameterString = "sessionId";
        String joinOnAttributeName = "roles";
        DAOUser user = DAOService.getSingleLeftJoinByPara(DAOUser.class, sessionId, parameterString, joinOnAttributeName);
        return user;
    }

    /* Author: Tom-Malte Seep
     * Revisited: Marvin Prüger
     * Function: gets id by mail
     * Reason: made getIdByMail public
     * UserStory/Task-ID:
     */
    /** gets id by mail
     * @param email email
     * @return      identifier
     */
    public static int getIdByMail(String email) {
        String parameterName = "email";
        DAOUser user = DAOService.getSingleByPara(DAOUser.class, email, parameterName);
        return user.getUid();
    }

    //creates
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: creates an entry
     * Reason:
     * UserStory/Task-ID:
     */
    /** creates an entry
     * @param email             email
     * @param password          password
     * @param name              name
     * @param privatDescription privatDescription
     * @param workDescription   workDescription
     * @param roles             roles
     * @return                  true if create was successfull
     */
    public static boolean createByEMail(String email, String password, String name, String privatDescription, String workDescription, List<DAORole> roles) {
        return DAOService.persist(new DAOUser(email, password, name, privatDescription, workDescription, roles));
    }

    //updates
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: updates by id
     * Reason:
     * UserStory/Task-ID:
     */
    /** updates the entry
     * @param id                identifier
     * @param name              name
     * @param privatDescription privatDescription
     * @param workDescription   workDescription
     * @param roles             roles
     * @return                  true if update was successfull
     */
    public static boolean updateById(int id, @Nullable String name, @Nullable String privatDescription, 
                                     @Nullable String workDescription, @Nullable List<DAORole> roles) {
        try {
            String joinOnAttributeName = "roles";
            DAOUser user = DAOService.getLeftJoinByID(id, DAOUser.class, joinOnAttributeName);
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
     * Function: updates by a DAOUser Object
     * Reason:
     * UserStory/Task-ID:
     */
    /** 
     * @param id   identifier
     * @param user DAOUser
     * @return     true if update was successfull
     */
    static boolean updateById(int id, DAOUser user) {
        return updateById(id, user.getName(), user.getPrivatDescription(), user.getWorkDescription(), user.getRoles());
    }
    
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: updates a List of DAOUsers
     * Reason:
     * UserStory/Task-ID:
     */
    /** updates a List of DAOUsers
     * @param users List of DAOUsers
     * @return      true if update was successfull
     */
    public static boolean updateUsers(List<DAOUser> users) {
        return DAOService.mergeList(users);
    }

    
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: updates by email
     * Reason:
     * UserStory/Task-ID:
     */
    /** updates by email
     * @param email             email
     * @param name              name
     * @param privatDescription privatDescription
     * @param workDescription   workDescription
     * @param roles             roles
     * @return                  true if update was successfull
     */
    public static boolean updateByEMail(String email, String name, String privatDescription, String workDescription, List<DAORole> roles) {
        return updateById(getIdByMail(email), name, privatDescription, workDescription, roles);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: updates the sessionID and sessionDate by id
     * Reason:
     * UserStory/Task-ID:
     */
    /** updates the sessionID and sessionDate by id
     * @param id          identifier
     * @param sessionId   sessionId
     * @param sessionDate sessionDate
     * @return            true if update was successfull
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
     * Function: clears the sessionid 
     * Reason:
     * UserStory/Task-ID:
     */
    /** clears the sessionid 
     * @param id identifier
     * @return   true if update was successfull
     */
    public static boolean emptySessionIdById(int id) {
        return updateSessionIdById(id, null, null);
    }

    //deletes
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: deletes entry by id
     * Reason:
     * UserStory/Task-ID:
     */
    /** deletes entry by id
     * @param id identifier
     * @return   true if delete is successfull
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
     * Function: deletes by email
     * Reason:
     * UserStory/Task-ID:
     */
    /** deletes by email
     * @param email email
     * @return      true if delete was successfull
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
     * Function: checks if email exists
     * Reason:
     * UserStory/Task-ID:
     */
    /** checks if email exists
     * @param email email
     * @return      true if entry with mail exists
     */
    public static boolean checkByEmail(String email) {
        String parameterName = "email";
        DAOAccount account = DAOService.getSingleByPara(DAOAccount.class, email, parameterName);
        return (account != null) ? true : false;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: checks if sessionId exists
     * Reason:
     * UserStory/Task-ID:
     */
    /** checks if sessionId exists
     * @param sessionId sessionId
     * @return          true if entry with sessionId exists
     */
    public static boolean checkSessionId(int sessionId) {
        try {
            String parameterName = "sessionId";
            DAOUser user = DAOService.getSingleByPara(DAOUser.class, sessionId, parameterName);
            if (user == null) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}