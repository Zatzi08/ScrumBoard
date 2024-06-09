package com.team3.project.service;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.User;
import com.team3.project.Classes.Role;
import com.team3.project.DAO.DAORole;
import com.team3.project.DAOService.DAORoleService;

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

    /**
     * gets all visual Roles of a real Role
     * @param role - real Role
     * @return Linked List of visual Roles
     * @throws Exception Null Param
     */
    public LinkedList<Role> getAllRolesByRole(Enumerations.Role role)throws Exception{
        if(role == null) throw new Exception("Null Role");
        // return DAORoleService.getAllRolesByRole(role);
        return null;
    }

    /**
     * create new visual Roles connected to a real Role
     * @param role - real Role
     * @param newRoleName - Name of new visual Role
     * @throws Exception Null or Empty Params
     */
    public void createVisualRole(String newRoleName, Enumerations.Role role) throws Exception{
        if(newRoleName == null) throw new Exception("Null new RoleName");
        if(newRoleName.isEmpty()) throw new Exception("Empty new RoleName");
        if(role == null) throw new Exception("Null Role");
        //DAORoleService.createVisualRole(newRoleName, role);
    }

    /**
     * delete visual Roles
     * @param role - real Role
     * @param roleName - Name of visual Role
     * @throws Exception Null or Empty Params
     */
    public void deleteVisualRole(String roleName, Enumerations.Role role) throws Exception{
        if(roleName == null) throw new Exception("Null new RoleName");
        if(roleName.isEmpty()) throw new Exception("Empty new RoleName");
        if(role == null) throw new Exception("Null Role");
        //DAORoleService.deleteVisualRole(newRoleName, role);
    }

    /**
     * change visual Roles Name
     * @param role - real Role
     * @param roleName - current visual Roles Name
     * @param newRoleName - new Name of visual Role
     * @throws Exception Null or Empty Params
     */
    public void changeVisualRoleName(String roleName, String newRoleName, Enumerations.Role role) throws  Exception{
        if(roleName == null) throw new Exception("Null current Role Name");
        if(roleName.isEmpty()) throw new Exception("Empty Role Name");
        if(newRoleName == null) throw new Exception("Null new Role Name");
        if(newRoleName.isEmpty()) throw new Exception("Empty new Role Name");
        if(role == null) throw new Exception("Null Role");
        //DAORoleService.changeVisualRole(roleName, newRoleName, role);
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



}
