package com.team3.project.service;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.UserStory;
import com.team3.project.DAOService.DAOUserStoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStoryService {
    private Enumerations enumerations = new Enumerations();

    // TODO: needs Database connection to implement create Object
    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public boolean createUserStory(String UserStoryContent){
        return false;
    }

    // TODO: needs Database connection to implement get Object
    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public UserStory getUserStory(int UserStoryID){
        return null; //logicToData.daoUserStoryService.getByID(UserStoryID);
    }

    // TODO: needs Database connection to implement Update Object
    /* Author: Henry L. Freyschmidt
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public boolean updateUserStory(UserStory userStory){//TODO: wie kann man die veränderten Attribute angeben
        return true;
    }
/*
    public void addUserStory(UserStory story) {
        if(story.getUserID() == -1){
            DAOUserStoryService.create(story.getName(),story.getDescription(), enumerations.getInt(story.getPriority()), story.getId());
        } else{
            //logicToData.daoUserStoryService.save(story.getUserID(), story.getName(), story.getDescription(), enumerations.getInt(story.getPriority()), story.getId());
        }
    }
*/
    public List<UserStory> getAllUserStorys() {
        return null; //logicToData.daoUserStoryService.getAll();
    }
}
