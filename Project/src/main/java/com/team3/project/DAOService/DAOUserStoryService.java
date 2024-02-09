package com.team3.project.DAOService;

import org.hibernate.Session;

import com.team3.project.Classes.UserStory;
import com.team3.project.DAO.DAOAccount;
import com.team3.project.DAO.DAOUserStory;

public class DAOUserStoryService {

    public static boolean create(String name,String description,int priority,String id){

        return false;
    }
/*
    public static List<UserStory> getAllUserStorys(){
        Session session = DAOSession.getNewSession();
        session.beginTransaction();
        List<DAOUserStory> user = session.createQuery( "select t from DAOAccount t" , DAOUserStory.class).list();
        if (!user.isEmpty()) {
            List<UserStory> UserStory; 
            for (DAOUserStory daoUserStory : user) {
                
                
            }



        }
        
            
        }

    }*/
    
}
