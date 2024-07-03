package com.team3.project.DAOService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.team3.project.DAO.DAOAccount;
import com.team3.project.DAO.DAOAuthorization;
import com.team3.project.DAO.DAORole;
import com.team3.project.DAO.DAOUser;

import io.micrometer.common.lang.Nullable;

public class DAOUserService {
    //gets 
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets all users
     * Reason:
     * UserStory/Task-ID: P4.D1
     */
    /** gets all entries <p>
     * without 'fetch'
     * @return List of DAOUser
     */
    public static List<DAOUser> getAll() {
        List<String> joinOnAttributeName = Arrays.asList("roles", "tasks", "authorization");
        return DAOService.getListLeftJoins(DAOUser.class, joinOnAttributeName);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets all userNames
     * Reason:
     * UserStory/Task-ID: 
     */
    /** returns all user.names
     * @return List of DAOUser.name
     */
    public static List<String> getAllUserNames() {
        return getAll().stream().map(DAOUser::getName).toList();
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets all entries with roles
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets all DAOUser
     * @return List of DAOUser with filled roles
     */
    public static List<DAOUser> getAllWithRoles() {
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
    public static DAOUser getWithRolesById(int id) {
        String joinAttributeName = "roles";
        return DAOService.getLeftJoinByID(id, DAOUser.class, joinAttributeName);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets entry by sessionId
     * Reason:
     * UserStory/Task-ID: S7.D3
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
        return getByMail(email).getId();
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: gets user
     * Reason: 
     * UserStory/Task-ID:
     */
    /** gets DAOUser by email
     * @param email email
     * @return      DAOUser
     */
    public static DAOUser getByMail(String email) {
        String parameterName = "email";
        DAOUser user = DAOService.getSingleByPara(DAOUser.class, email, parameterName);
        return user;
    }

    /* Author: Tom-Malte Seep
     * Revisited:
     * Function: gets with authorization
     * Reason: 
     * UserStory/Task-ID:
     */
    /** gets 
     * @param id identifier
     * @return   DAOUser
     */
    public static DAOUser getWithAuthorizationById(int id) {
        String parameterName = "authorization";
        DAOUser user = DAOService.getLeftJoinByID(id, DAOUser.class, parameterName);
        return user;
    }

    //creates
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: creates an entry
     * Reason:
     * UserStory/Task-ID: A2.D1, S7.D2
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
    public static boolean createByEMail(String email, String password, @Nullable String name, @Nullable String privatDescription, 
                                        @Nullable String workDescription, @Nullable List<DAORole> roles, @Nullable DAOAuthorization authorization,
                                        @Nullable String sessionId, @Nullable String sessionDate, boolean newSessionId) {
        DAOAuthorization daoAuthorization = DAOAuthorizationService.getByAuthorization(1);
        if (authorization != null) {
            daoAuthorization = DAOAuthorizationService.getByAuthorization(authorization.getAuthorization());
        }
        String hashedPassword = DAOUserService.hash(password);
        if (newSessionId) {
            String createdSessionId = (sessionId != null) ? sessionId : createSessionId();
            String createdSessionDate = (sessionDate != null) ? sessionDate : createSessionDate();
            return DAOService.merge(new DAOUser(email, hashedPassword, name, privatDescription, workDescription, DAOAuthorizationService.filterRolesByAuthorization(daoAuthorization, roles), 
                                                  createdSessionId, createdSessionDate, daoAuthorization));
        }
        return DAOService.merge(new DAOUser(email, hashedPassword, name, privatDescription, workDescription, roles, daoAuthorization));
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: creates an entry
     * Reason: 
     * UserStory/Task-ID: A2.D1, S7.D2
     */
    public static boolean createByEMail(String email, String password, @Nullable String name, @Nullable String privatDescription, 
                                        @Nullable String workDescription, @Nullable List<DAORole> roles, int authorization,
                                        @Nullable String sessionId, @Nullable String sessionDate, boolean newSessionId) {
        DAOAuthorization daoAuthorization = DAOAuthorizationService.getByAuthorization(1);
        if (authorization > 0) {
            daoAuthorization = DAOAuthorizationService.getByAuthorization(authorization);
        }
        String hashedPassword = DAOUserService.hash(password);
        return createByEMail(email, hashedPassword, name, privatDescription, workDescription, roles, daoAuthorization, sessionId, sessionDate, newSessionId);
    }

    //updates
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: updates by id
     * Reason:
     * UserStory/Task-ID: R1.D3, A5.D1, P2.D1
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
                                     @Nullable String workDescription, @Nullable List<DAORole> roles, @Nullable DAOAuthorization authorization,
                                     @Nullable String sessionId, @Nullable String sessionDate,
                                     boolean newSessionId) {
        try {
            DAOAuthorization daoAuthorization = null;
            if (authorization != null) {
                daoAuthorization = DAOAuthorizationService.getByAuthorization(authorization.getAuthorization());
            }
            List<String> joinOnAttributeNames = Arrays.asList("authorization", "roles");
            DAOUser user = DAOService.getSingleLeftJoinsById(id, DAOUser.class, joinOnAttributeNames);
            if (user != null) {
                if (newSessionId) {
                    String createdSessionId = (sessionId != null) ? sessionId : createSessionId();
                    String createdSessionDate = (sessionDate != null) ? sessionDate : createSessionDate();
                    user.cloneDAOUser(new DAOUser(name, privatDescription, workDescription, DAOAuthorizationService.filterRolesByAuthorization(user.getAuthorization(), roles), daoAuthorization, createdSessionId, createdSessionDate));
                } else {
                    user.cloneDAOUser(new DAOUser(name, privatDescription, workDescription, DAOAuthorizationService.filterRolesByAuthorization(user.getAuthorization(), roles), daoAuthorization));
                }
                return DAOService.merge(user);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: updates an entry
     * Reason: 
     * UserStory/Task-ID: R1.D3, A5.D1, P2.D1
     */
    public static boolean updateById(int id, @Nullable String name, @Nullable String privatDescription, 
                                     @Nullable String workDescription, @Nullable List<DAORole> roles, int authorization,
                                     @Nullable String sessionId, @Nullable String sessionDate,
                                     boolean newSessionId) {
        return updateById(id, name, privatDescription, workDescription, roles, DAOAuthorizationService.getByAuthorization(authorization), sessionId, sessionDate, newSessionId);
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: updates by a DAOUser Object
     * Reason:
     * UserStory/Task-ID:
     */
    /*
     * @param id           identifier
     * @param user         DAOUser
     * @param newSessionId create a newSessionId
     * @return     true if update was successfull
     *//*
    static boolean updateById(int id, DAOUser user, boolean newSessionId) {
        return updateById(id, user.getName(), user.getPrivatDescription(), user.getWorkDescription(), user.getRoles(), 
                          user.getSessionId(), user.getSessionDate(), newSessionId);
    } */
    
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
     * UserStory/Task-ID: P2.D1
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
        return updateById(getIdByMail(email), name, privatDescription, workDescription, roles, null, null, null, false);
    }
    
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: updates by email
     * Reason:
     * UserStory/Task-ID: P2.D1
     */
    /** updates by email
     * @param email             email
     * @param name              name
     * @param privatDescription privatDescription
     * @param workDescription   workDescription
     * @param roles             roles
     * @param sessionId         sessionId
     * @param sessionDate       sessionDate
     * @param newSessionId      is creating a new sessionId
     * @return                  true if update was successfull
     */
    public static boolean updateByEMail(String email, String name, String privatDescription, String workDescription, List<DAORole> roles,
                                        String sessionId, String sessionDate, boolean newSessionId) {
        return updateById(getIdByMail(email), name, privatDescription, workDescription, roles, null, null, null, newSessionId);
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
     * UserStory/Task-ID: S7.D4
     */
    /** clears the sessionid 
     * @param id identifier
     * @return   true if update was successfull
     */
    public static boolean emptySessionIdById(int id) {
        return updateSessionIdById(id, null, null);
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: updates authorization
     * Reason: 
     * UserStory/Task-ID:
     */
    public static boolean updateAuthorizationById(int id, int authorization) {
        String joinOnAttributeName = "authorization";
        DAOUser user = DAOService.getLeftJoinByID(id, DAOUser.class, joinOnAttributeName);
        int maxAuthorization = 4;
        if (user != null && authorization <= maxAuthorization) {
            user.setAuthorization(DAOAuthorizationService.getByAuthorization(authorization));
            return DAOService.merge(user);
        }
        return false;
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: updates authorization
     * Reason: 
     * UserStory/Task-ID:
     */
    public static boolean updateAuthorizationById(int id, DAOAuthorization daoAuthorization) {
        return updateAuthorizationById(id, daoAuthorization.getAuthorization());
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: updates roles
     * Reason: 
     * UserStory/Task-ID: R5.D2, R1.D3
     */
    public static boolean updateRolesById(int id, List<DAORole> roles) {
        List<String> joinOnAttributeNames = Arrays.asList("authorization", "roles");
        DAOUser daoUser = DAOService.getSingleLeftJoinsById(id, DAOUser.class, joinOnAttributeNames);
        if (daoUser != null) {
            boolean addrole = false;
            for (DAORole daoRole : roles) {
                if (DAORoleService.checkAuthorizationById(daoRole.getId(), daoUser.getAuthorization())) {
                    daoUser.getRoles().add(daoRole);
                    addrole = true;
                }
            }
            return addrole && DAOService.merge(daoUser);
        }
        return false;
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: adds role
     * Reason: 
     * UserStory/Task-ID: R5.D2, R1.D3
     */
    public static boolean updateAddRoleById(int id, DAORole role) {
        List<String> joinOnAttributeNames = Arrays.asList("authorization", "roles");
        DAOUser daoUser = DAOService.getSingleLeftJoinsById(id, DAOUser.class, joinOnAttributeNames);
        if (DAORoleService.checkAuthorizationById(role.getId(), daoUser.getAuthorization()) && daoUser != null) {
            daoUser.getRoles().add(role);
            return DAOService.merge(daoUser);
        }
        return false;
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
    public static boolean checkSessionId(String sessionId) {
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

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: creates a new sessionId
     * Reason: 
     * UserStory/Task-ID:
     */
    private static String createSessionId() {
        List<String> sessionIds = new ArrayList<String>();
        DAOUserService.getAll().stream().forEach(user -> {
            if (user.getSessionId() != null && user.getSessionId() != "") {
                sessionIds.add(user.getSessionId());
            }
        });
        int minBound = 10000000, maxBound = 100000000;
        String sessionId = "";
        while (sessionIds.contains(sessionId)) {
            sessionId = Integer.toString(new Random().nextInt(minBound, maxBound));
        }
        return sessionId;
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: creates a SessionDate
     * Reason: 
     * UserStory/Task-ID:
     */
    private static String createSessionDate() {
        return LocalDate.now().toString();
    }

    static String hash(String password) {
        if (password == null) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (int i = 0; i < encodedhash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedhash[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return null;
    }
}