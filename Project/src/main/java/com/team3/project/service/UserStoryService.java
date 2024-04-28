package com.team3.project.service;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.Task;
import com.team3.project.Classes.UserStory;
import com.team3.project.DAO.DAOUserStory;
import com.team3.project.DAOService.DAOUserStoryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserStoryService {

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: gibt UserStory aus DB mit bestimmter id
     * Grund: /
     * UserStory/Task-ID: /
     */
    public UserStory getUserStory(int userStoryID) throws Exception {
        if (userStoryID == -1) throw new Exception("Null UserStoryID");
        return null; //logicToData.daoUserStoryService.getByID(UserStoryID);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Erstellt neue oder updatet existente UserStory in DB
     * Grund: /
     * UserStory/Task-ID: /
     */
    public void saveUserStory(String name, String desc, int prio, int id) throws Exception {
        if (name == null) throw new Exception("Null Story-Name");
        if (desc == null) throw new Exception("Null Story-Desc");
        if (prio <= -1) throw new Exception("Null Story-prio");
        if (id < -1) throw new Exception("Null Story-id");
        if(id == -1){
            DAOUserStoryService.create(name,desc, prio);
        } else{
            DAOUserStory userStory = DAOUserStoryService.getById(id);
            if (userStory != null){
                if (!userStory.getName().equals(name)) DAOUserStoryService.updateName(id,name);
                if (!userStory.getDescription().equals(desc)) DAOUserStoryService.updateDescription(id,desc);
                if (userStory.getPriority() != prio) DAOUserStoryService.updatePriority(id, prio);
            }
        }
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: gibt alle UserStorys aus DB aus
     * Grund: /
     * UserStory/Task-ID: /
     */
    public List<UserStory> getAllUserStorys() {
        List<DAOUserStory> user = DAOUserStoryService.getAll();
        if (user != null) {
            List<UserStory> userStories = new LinkedList<>();
            for (DAOUserStory daoUserStory : user) {
                UserStory toAdd = new UserStory(daoUserStory.getName(), daoUserStory.getDescription(), daoUserStory.getPriority(), daoUserStory.getId());
                userStories.add(toAdd);
            }
            return userStories;
        }
        return null;
    }

    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: löscht existente UserStory in DB anhand id
     * Grund: /
     * UserStory/Task-ID: /
     */
    public void deleteUserStory(int uid) throws Exception {
        if (uid <= -1) throw new Exception("Null usid");
        DAOUserStoryService.deleteById(uid);
    }
}