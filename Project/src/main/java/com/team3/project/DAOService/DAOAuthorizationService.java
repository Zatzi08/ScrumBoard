package com.team3.project.DAOService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.team3.project.DAO.DAOAuthorization;
import com.team3.project.DAO.DAORole;

public class DAOAuthorizationService {
    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: gets all authorizations
     * Reason: 
     * UserStory/Task-ID:
     */
    public static List<DAOAuthorization> getAll() {
        checkAuthorizations();
        return DAOService.getAll(DAOAuthorization.class);
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: gets entry by id
     * Reason: 
     * UserStory/Task-ID:
     */
    public static DAOAuthorization getByAuthorization(int authorization) {
        checkAuthorizations();
        String parameterName = "authorization";
        return DAOService.getSingleByPara(DAOAuthorization.class, authorization, parameterName);
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: creates the default authorizations
     * Reason: 
     * UserStory/Task-ID: R1.D1
     */
    private static boolean createDefaultAuthorizations(List<DAOAuthorization> daoAuthorizations) {
        List<Integer> authorizations = daoAuthorizations.stream().map(DAOAuthorization::getAuthorization).toList();
        List<DAOAuthorization> defaultDAOAuthorizations = Arrays.asList(
            new DAOAuthorization("Developer", 1),
            new DAOAuthorization("Product Owner", 2),
            new DAOAuthorization("Manager", 3),
            new DAOAuthorization("Admin", 4)
        );
        List<DAOAuthorization> persistList = defaultDAOAuthorizations.stream()
                    .filter(defaultAuthorization -> !authorizations.contains(defaultAuthorization.getAuthorization()))
                    .toList();
        return DAOService.persistList(persistList);
    }
    
    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: checks if the default authorizations exists
     * Reason: 
     * UserStory/Task-ID:
     */
    static boolean checkAuthorizations() {
        List<DAOAuthorization> daoAuthorizations = DAOService.getAll(DAOAuthorization.class);
        if (daoAuthorizations == null || daoAuthorizations.size() != 4) {
            return createDefaultAuthorizations(daoAuthorizations);
        }
        return true;
    }

    /* Author: Tom-Malte Seep
     * Revisited: 
     * Function: filters the given roles list for the given authorization
     * Reason: 
     * UserStory/Task-ID:
     */
    static List<DAORole> filterRolesByAuthorization(DAOAuthorization authorization, List<DAORole> roles) {
        String joinOnAttributeName = "roles";
        DAOAuthorization daoAuthorization = DAOService.getLeftJoinByID(authorization.getId(), DAOAuthorization.class, joinOnAttributeName);
        if (daoAuthorization != null && roles != null) {
            return daoAuthorization.getRoles().stream().filter(sDaoRole -> roles.stream().map(DAORole::getName).collect(Collectors.toList()).contains(sDaoRole.getName())).toList();
        }
        return null;
    }
}
