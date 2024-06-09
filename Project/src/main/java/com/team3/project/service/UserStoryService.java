package com.team3.project.service;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.UserStory;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAO.DAOUserStory;
import com.team3.project.DAOService.DAOTaskService;
import com.team3.project.DAOService.DAOUserStoryService;
import org.springframework.stereotype.Service;

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
    /**
     * gets an UserStory out of Database
     * @param userStoryID - Identifier
     * @return UserStory-Object
     * @throws Exception Null Params or Object not found
     */
    public UserStory getUserStory(int userStoryID) throws Exception {
        if (userStoryID == -1) throw new Exception("Invalid UserStoryID");

        DAOUserStory story = DAOUserStoryService.getById(userStoryID);
        if (story == null) throw new Exception("UserStory not found");
        return new UserStory(story.getName(), story.getDescription(), story.getPriority(), story.getId());
    }

    public UserStory getUserStoryByName(String name){ //nur für Tests notwendig
        DAOUserStory daoUserStory = DAOUserStoryService.getByName(name);
        return new UserStory(daoUserStory.getName(), daoUserStory.getDescription(), daoUserStory.getPriority(),daoUserStory.getId());
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Erstellt neue oder updatet existente UserStory in DB
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     * create/save UserStory-Object in Database
     * @param userStory -
     * @throws Exception Null Params or Object not found
     */
    public void saveUserStory(UserStory userStory) throws Exception {
        if(userStory == null) throw new Exception("Null User-Story");
        if (userStory.getName() == null) throw new Exception("Null Story-Name");
        if (userStory.getDescription() == null) throw new Exception("Null Story-Desc");
        if (userStory.getPriorityAsInt() <= -1) throw new Exception("Null Story-prio");
        if(userStory.getID() <= -1){
            DAOUserStoryService.create(userStory.getName(),userStory.getDescription(), userStory.getPriorityAsInt());
        } else{
            DAOUserStory dus = DAOUserStoryService.getById(userStory.getID());
            if (dus != null){
                if (!dus.getName().equals(userStory.getName())) 
                    DAOUserStoryService.updateName(dus.getId(),userStory.getName());
                if (!dus.getDescription().equals(userStory.getDescription())) 
                    DAOUserStoryService.updateDescription(dus.getId(),userStory.getDescription());
                if (dus.getPriority() != userStory.getPriorityAsInt()) DAOUserStoryService.updatePriority(dus.getId(), userStory.getPriorityAsInt());
            } else throw new Exception("UserStory not found");
        }
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: gibt alle UserStorys aus DB aus
     * Grund: /
     * UserStory/Task-ID: /
     */
    /**
     * gets all UserStorys out of Database
     * @return LinkedList<UserStory> or Null
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

    /**
     * deletes UserStory and all related Tasks out of Database
     * @param uid - Identifier
     * @throws Exception Null Params
     */
    public void deleteUserStoryAndLinkedTasks(int uid) throws Exception {
        if (uid <= -1) throw new Exception("Null usid");
        List <DAOTask> tasks = DAOTaskService.getListByUserStoryId(uid);
        if(tasks != null) tasks.forEach(x -> DAOTaskService.deleteById(x.getId()));
        DAOUserStoryService.deleteById(uid);
    }
}