package com.team3.project.DAOService;

import java.util.List;

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
}
