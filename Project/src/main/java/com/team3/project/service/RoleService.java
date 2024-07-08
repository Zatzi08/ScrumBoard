package com.team3.project.service;

import com.team3.project.Classes.Enumerations;
import com.team3.project.DAO.DAOUser;
import com.team3.project.Classes.Role;
import com.team3.project.DAO.DAORole;
import com.team3.project.DAOService.DAORoleService;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.Websocket.WebsocketObserver;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    WebsocketObserver observer;
    @Autowired
    private Enumerations enumerations;

    public RoleService() {

    }

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
        boolean responce = DAORoleService.create(newRoleName, getAuthorizationFromRoleAsInt(role));
        if (responce) {
            List<DAORole> drl = DAORoleService.getAll();
            DAORole dr = drl.get(drl.size()-1);
            try {
                observer.sendToRoleGroup(2, new Role(dr.getId(),dr.getName()));
            } catch (Exception e) {
                System.out.println("Observer nicht initialisiert");
            }
        }
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
    public void deleteVisualRoleById(int id) throws Exception {
        DAORole dr = DAORoleService.getByID(id);
        if (dr == null) throw new Exception("Role not found");
        boolean responce = DAORoleService.deleteById(id);
        if (responce) {
            try {
                observer.sendToRoleGroup(2, new Role(dr.getId(),dr.getName()));
            } catch (Exception e) {
                System.out.println("Observer nicht initialisiert");
            }
        }
    }

    /**
     * change visual Roles Name
     * @param id - role id
     * @param newRoleName - new Name of visual Role
     * @throws Exception Null or Empty Params
     */
    public void changeVisualRoleName(int id, @Nonnull final String newRoleName) throws  Exception{
        if(newRoleName.isEmpty()) throw new Exception("Empty new Role Name");
        boolean responce = DAORoleService.updateNameById(id, newRoleName);
        if (responce) {
            //DAORole dr = DAORoleService.getByID(id);
            try {
                observer.sendToRoleGroup(2, new Role(id,newRoleName));
            } catch (Exception e) {
                System.out.println("Observer nicht initialisiert");
            }
        }
    }

    /**
     * add visual Role to User
     * @param visualRole- visual Role to be attributed
     * @throws Exception Null or Empty or out of domain Params
     */
    public void addVisualRole(int uID, Role visualRole) throws Exception{
        if(visualRole == null) throw new Exception("Null visual Role");
        if(visualRole.getID() < 0) throw new Exception("Invalid visual RoleID");
        DAOUser du = DAOUserService.getWithAuthorizationById(uID);
        if (du == null) throw new Exception("User not found");
        DAORole dr = DAORoleService.getByID(visualRole.getID());
        if (dr == null) throw new Exception("Visual Role not found");
        if (du.getAuthorization().getAuthorization() != dr.getAuthorizations().get(0).getAuthorization()) throw new Exception("Mismatching Authorisation");
        boolean responce = DAOUserService.updateAddRoleById(du.getId(), dr);
        if (responce) {
            try {
                observer.sendToProfileByID(2, visualRole);
            } catch (Exception e) {
                System.out.println("Observer nicht initialisiert");
            }
        }
    }

    /* Author: Henry van Rooyen
     * Revisited: /
     * Function: helperFunction -> converts authorization form role to int
     * UserStory/Task-ID: R2.B1 & B2
     */
    public int getAuthorizationFromRoleAsInt(@Nonnull final Enumerations.Role role){
        return Enumerations.getAuthorizationFromRoleAsInt(role);
    }

    public Enumerations.Role getRoleByInt(int roleID) throws Exception {
        if (roleID > 4 || roleID < 1) throw new Exception("Invalid RoleID");
        return Enumerations.getRoleFromInt(roleID);
    }

    public void addRolesToUser(int uID, LinkedList<Role> roleList) {
        for (Role role : roleList){
            try {
                addVisualRole(uID,role);
            } catch (Exception ignored){}
        }
    }

    public List<Role> getAllVisualRoles() {
        List<Role> rl = new LinkedList<Role>();
        for(DAORole dr : DAORoleService.getAll()){
            Role toAdd = new Role(dr.getId(),dr.getName());
            toAdd.setAuth(Enumerations.getRoleFromInt(dr.getAuthorizations().get(0).getAuthorization()));
            rl.add(toAdd);
        }
        return rl;
    }

    public static List<Role> toRoleList(List<DAORole> drl){
        List<Role> rl = new LinkedList<Role>();
        for (DAORole dr : drl){
            Role toAdd = new Role(dr.getId(),dr.getName());
            toAdd.setAuth(Enumerations.getRoleFromInt(dr.getAuthorizations().get(0).getAuthorization()));
            rl.add(toAdd);
        }
        return rl;
    }

    public static List<DAORole> toRoleList_DAO(List<Role> roles) {
        List<DAORole> rl = new LinkedList<DAORole>();
        for (Role r : roles){
            rl.add(DAORoleService.getByID(r.getID()));
        }
        return rl;
    }

    public Object getAllVisualRolesByRoleOfUserBySession(String sessionID) throws Exception {
        DAOUser du = DAOUserService.getBySessionId(sessionID);
        if (du == null) throw new Exception("User not found");
        return toRoleList(DAORoleService.getByAuthorization(du.getAuthorization().getAuthorization()));
    }
}
