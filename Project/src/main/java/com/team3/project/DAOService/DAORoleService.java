package com.team3.project.DAOService;

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
        List<DAORole> roles = DAOService.getAll(DAORole.class);
        return roles;
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

    public static List<DAORole> getByAuthorization(int authorization) {
        String joinOnAttributeName = "authorization";
        List<DAORole> roles = DAOService.getAllLeftJoin(DAORole.class, joinOnAttributeName);
        if (roles == null) {
            return null;
        }
        return roles.stream()
            .filter(role -> role.getAuthorizations().stream()
                .filter(auth -> auth.getAuthorization() == authorization)
            .toList().size() > 0).toList();
    }

    public static List<DAORole> getByAuthorization(DAOAuthorization authorization) {
        return getByAuthorization(authorization.getAuthorization());
    }
    
    public static boolean create(String name, DAOAuthorization daoAuthorization) {
        try {
            DAOService.persist(new DAORole(name, Arrays.asList(daoAuthorization)));
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public static boolean create(String name, int authorization) {
        return create(name, DAOAuthorizationService.getByAuthorization(authorization));
    }
    
    public static boolean updateNameById(int id, String name) {
        DAORole role = getByID(id);
        if (role != null) {
            role.setName(name);
            return DAOService.merge(role);
        }
        return false;
    }

    static boolean deleteById(int id) {
        DAORole role = DAOService.getByID(id, DAORole.class);
        if (role != null) {
            return DAOService.delete(role);
        }
        return true;
    }

    static boolean checkAuthorizationById(int id, DAOAuthorization authorization) {
        return checkAuthorizationIdById(id, authorization.getId());
    }

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
