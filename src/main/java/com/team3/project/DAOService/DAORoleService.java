package com.team3.project.DAOService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.team3.project.DAO.DAOAuthorization;
import com.team3.project.DAO.DAORole;

public class DAORoleService {
    //gets
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets all roles
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets all roles
     * @return list of DAORoles
     */
    public static List<DAORole> getAll() {
        List<String> joinOnAttributeName = Arrays.asList("authorizations" /*, users */);
        return DAOService.getAllLeftJoin(DAORole.class, joinOnAttributeName);
    }

    
    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets a role by ID
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets a role by ID
     * @param id identifier
     * @return   DAORole
     */
    public static DAORole getByID(int id) {
        DAORole role = DAOService.getByID(id, DAORole.class);
        return role;
    }

    /* Author: Tom-Malte Seep
     * Revisited: /
     * Function: gets a role by name
     * Reason:
     * UserStory/Task-ID:
     */
    /** gets a role by name
     * @param name name
     * @return     DAORole
     */
    public static DAORole getByName(String name) {
        String parameterName = "name";
        DAORole role = DAOService.getSingleByPara(DAORole.class, name, parameterName);
        return role;
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: get roles by authorization
     * Reason: 
     * UserStory/Task-ID:
     */
    public static List<DAORole> getByAuthorization(int authorization) {
        String joinOnAttributeName = "authorizations";
        List<DAORole> roles = DAOService.getAllLeftJoin(DAORole.class, joinOnAttributeName);
        if (roles == null) {
            return new ArrayList<DAORole>();
        }
        return roles.stream()
            .filter(role -> role.getAuthorizations().stream()
                .filter(auth -> auth.getAuthorization() == authorization)
            .toList().size() > 0).toList();
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: get roles by authorization
     * Reason: 
     * UserStory/Task-ID: R2.D2
     */
    public static List<DAORole> getByAuthorization(DAOAuthorization authorization) {
        return getByAuthorization(authorization.getAuthorization());
    }

    public static DAORole getWithAuthorizationById(int id) {
        String joinOnAttributeName = "authorizations";
        return DAOService.getLeftJoinByID(id, DAORole.class, joinOnAttributeName);
    }
    
    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: creates a role
     * Reason: 
     * UserStory/Task-ID: R2.D3
     */
    public static boolean create(String name, DAOAuthorization daoAuthorization) {
        try {
            DAOService.persist(new DAORole(name, Arrays.asList(daoAuthorization)));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: creates a role
     * Reason: 
     * UserStory/Task-ID:
     */
    public static boolean create(String name, int authorization) {
        return create(name, DAOAuthorizationService.getByAuthorization(authorization));
    }
    
    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: updates the name
     * Reason: 
     * UserStory/Task-ID: R3.D1
     */
    public static boolean updateNameById(int id, String name) {
        DAORole role = getByID(id);
        if (role != null) {
            role.setName(name);
            return DAOService.merge(role);
        }
        return false;
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: deletes a role
     * Reason: 
     * UserStory/Task-ID: R4.D1
     */
    public static boolean deleteById(int id) {
        DAORole role = DAOService.getByID(id, DAORole.class);
        if (role != null) {
            return DAOService.delete(role);
        }
        return true;
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: checks if authorization is correct
     * Reason: 
     * UserStory/Task-ID:
     */
    static boolean checkAuthorizationById(int id, DAOAuthorization authorization) {
        return checkAuthorizationIdById(id, authorization.getId());
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: checks if authorization is correct
     * Reason: 
     * UserStory/Task-ID:
     */
    static boolean checkAuthorizationIdById(int id, int authorizationId) {
        String joinOnAttributeName = "authorizations";
        DAORole daoRole = DAOService.getLeftJoinByID(id, DAORole.class, joinOnAttributeName);
        DAOAuthorization daoAuthorization = DAOService.getByID(authorizationId, DAOAuthorization.class);
        if (daoRole != null && daoRole.getAuthorizations() != null && daoAuthorization != null) {
            return daoRole.getAuthorizations().stream().map(DAOAuthorization::getAuthorization).collect(Collectors.toList()).contains(daoAuthorization.getAuthorization());
        }
        return false;
    }
}
