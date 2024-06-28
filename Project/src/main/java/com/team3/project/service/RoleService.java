package com.team3.project.service;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.User;
import com.team3.project.Classes.Role;
import com.team3.project.DAO.DAORole;
import com.team3.project.DAOService.DAORoleService;
import jakarta.annotation.Nonnull;

import java.util.LinkedList;
import java.util.List;

public class RoleService {

    /**
     * gets all Roles of one User
     * @param uID - Identifier
     * @return LinkedList<Roll>
     * @throws Exception Null Params or Object not found
     */
    public List<Role> getRollesByUSID(int uID) throws Exception {
        if (uID < 0) throw new Exception("Null uID");
        List<Role> roles = new LinkedList<>();
        List<DAORole> drs = DAORoleService.getAll();
        if (!drs.isEmpty()){
            for (DAORole dr : drs){
                Role toAdd = new Role(dr.getId(),dr.getName());
                roles.add(toAdd);
            }
        } else throw new NullPointerException("No Role found");
        return roles;
    }

    /**
     * gets Role in Database
     * @param id - Identifier
     * @return Role-Object
     * @throws Exception Null Params or Object not found
     */
    public Role getRoleById(int id) throws Exception {
        if (id < 0) throw new Exception("Null id");
        DAORole dr = DAORoleService.getByID(id);
        if (dr == null) throw new NullPointerException("No Role found");
        return new Role(dr.getId(),dr.getName());
    }

    /**
     * changes Rights of User
     * @param uID - Identifier
     * @param newAuthority - new Rights
     * @throws Exception Params out of domain
     */
    public void changeAuthority(int uID, int newAuthority) throws Exception{
        if(uID < 0) throw new Exception("Invalid UserID");
        if(newAuthority < 0 || newAuthority > 3) throw new Exception("Invalid new Authority");
        //DAORoleService.changeAuthority(uID,newAuthority);
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: 27.6 by Henry van Rooyen
     * Function: gets all visualRoles based on role
     * UserStory/Task-ID: R2.B1
     */
    /**
     * gets all visual Roles of a real Role
     * @param role - real Role
     * @return List of visual Roles
     */
    public List<DAORole> getAllVisualRolesByRole(@Nonnull final Enumerations.Role role){
        return DAORoleService.getByAuthorization(getAuthorizationFromRoleAsInt(role));
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: 27.6 by Henry van Rooyen
     * Function: creates a visualRole based on role
     * UserStory/Task-ID: R2.B2
     */
    /**
     * create new visual Roles connected to a real Role
     * @param role - real Role
     * @param newRoleName - Name of new visual Role
     * @throws Exception Null or Empty Params
     */
    public void createVisualRole(@Nonnull final String newRoleName, @Nonnull final Enumerations.Role role) throws Exception{
        if(newRoleName.isEmpty()) throw new Exception("Empty new RoleName");
        DAORoleService.create(newRoleName, getAuthorizationFromRoleAsInt(role));
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: 28.6 by Henry van Rooyen
     * Function: deletes visualRole based on id
     * UserStory/Task-ID: R2.B2
     */
    /**
     * delete visual Role
     * @param id - role id
     */
    public void deleteVisualRoleById(int id){
        DAORoleService.deleteById(id);
    }

    /**
     * change visual Roles Name
     * @param id - role id
     * @param newRoleName - new Name of visual Role
     * @throws Exception Null or Empty Params
     */
    public void changeVisualRoleName(int id, @Nonnull final String newRoleName) throws  Exception{
        if(newRoleName.isEmpty()) throw new Exception("Empty new Role Name");
        DAORoleService.updateNameById(id, newRoleName);
    }

    /**
     * change visual Roles Name
     * @param role - real Role
     * @param user -  user which recieves visual Role
     * @param visualRole- visual Role to be attributed
     * @throws Exception Null or Empty or out of domain Params
     */
    public void saveVisualRole(Role visualRole, User user, Enumerations.Role role) throws Exception{
        if(visualRole == null) throw new Exception("Null visual Role");
        if(visualRole.getID() < 0) throw new Exception("Invalid visual RoleID");
        if(role == null) throw new Exception("Null Role");
        if(user == null) throw new Exception("Null user");
        if(user.getID() < 0) throw new Exception("Invalid UserID");
        //DAOUserService.addRole(newRole, user.getID());

    }

    /* Author: Henry van Rooyen
     * Revisited: /
     * Function: helperFunction -> converts authorization form role to int
     * UserStory/Task-ID: R2.B1 & B2
     */
    public int getAuthorizationFromRoleAsInt(@Nonnull final Enumerations.Role role){
        return Enumerations.getAuthorizationFromRoleAsInt(role);
    }

}
