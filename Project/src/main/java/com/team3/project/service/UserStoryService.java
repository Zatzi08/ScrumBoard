package com.team3.project.service;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.UserStory;
import org.springframework.stereotype.Service;

@Service
public class UserStoryService {

    // TODO: needs Database connection to implement create Object
    public boolean createUserStory(String UserStoryContent){
        return false;
    }

    // TODO: needs Database connection to implement get Object
    public UserStory getUserStory(int UserStoryID){
        return null;
    }

    // TODO: needs Database connection to implement Update Object
    public boolean updateUserStory(UserStory userStory){//TODO: wie kann man die veränderten Attribute angeben
        return true;
    }

    // TODO: Delete later
    public UserStory getUserStoryT() {
        return new UserStory(1,"Krasse Story", Enumerations.Priority.low);
    }
}
