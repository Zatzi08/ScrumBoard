package com.team3.project.service;

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
}
