package com.team3.project.service;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.UserStory;
import com.team3.project.DAO.DAOUserStory;
import com.team3.project.DAOService.DAOUserStoryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserStoryService {
    private Enumerations enumerations = new Enumerations();

    // TODO: needs Database connection to implement get Object
    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: /
     */
    public UserStory getUserStory(int userStoryID) throws Exception {
        if (userStoryID == -1) throw new Exception("Null UserStoryID");
        return null; //logicToData.daoUserStoryService.getByID(UserStoryID);
    }

    public void addUserStory(UserStory story) throws Exception {
        if (story == null) throw new Exception("Null Story");
        if(story.getId() == -1){
            DAOUserStoryService.create(story.getName(),story.getDescription(), enumerations.getInt(story.getPriority()));
        } else{
            if (DAOUserStoryService.getByID(story.getId()) != null){
                DAOUserStoryService.updateName(story.getId(),story.getName());
                DAOUserStoryService.updateDescription(story.getId(),story.getDescription());
                Enumerations prio = new Enumerations();
                DAOUserStoryService.updatePriority(story.getId(), prio.getInt(story.getPriority()));
            }
        }
    }

    public List<UserStory> getAllUserStorys() {
        List<DAOUserStory> user = DAOUserStoryService.getAll();
        if (user != null) {
            List<UserStory> userStories = new LinkedList<>();
            for (DAOUserStory daoUserStory : user) {
                Enumerations prio = new Enumerations();
                UserStory toAdd = new UserStory(daoUserStory.getName(), daoUserStory.getDescription(), daoUserStory.getPriority(), daoUserStory.getId());
                userStories.add(toAdd);
            }
            return userStories;
        }
        return null;
    }
}