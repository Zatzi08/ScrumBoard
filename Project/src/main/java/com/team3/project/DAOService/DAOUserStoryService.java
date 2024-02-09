package com.team3.project.DAOService;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.Session;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.UserStory;
import com.team3.project.DAO.DAOAccount;
import com.team3.project.DAO.DAOUserStory;

public class DAOUserStoryService {
    
    public static List<UserStory> getAll(){
        List<UserStory> userstories = new LinkedList<UserStory>();
        Session session = DAOSession.getNewSession();
        session.beginTransaction();
        List<DAOUserStory> user = session.createQuery( "from DAOUserStory" , DAOUserStory.class).list();
        if (!user.isEmpty()) {
            for (DAOUserStory daoUserStory : user) {
                Enumerations prio = new Enumerations();
                UserStory toAdd = new UserStory(daoUserStory.getName(), daoUserStory.getDescription(),prio.IntToPriority(daoUserStory.getPriority()), daoUserStory.getId());
                userstories.add(toAdd);
            }
            session.close();
            return userstories;
        }
        session.close();
        return userstories; //hier error?
    }

    public static boolean create(String name,String description,int priority,int id){
        DAOUserStory tosave = new DAOUserStory( name, description, priority, id);
        if(true/*checkname/id*/){
            Session session = DAOSession.getNewSession();
            session.beginTransaction();
            session.persist(tosave);
            session.getTransaction().commit();
            session.close();
            return true;
        }
        return false;
    }

    public static boolean delete(String name){
        if(true /*checkname*/){
            Session session = DAOSession.getNewSession();
            session.beginTransaction();
            DAOUserStory story = session.createQuery( "from DAOUserStory where name = ?1" , DAOUserStory.class).setParameter(1, name).uniqueResult();
            session.remove(story);
            session.getTransaction().commit();
            session.close();
            return true;
        }
    return false;
    }

    public static boolean updateName(String name,String newName){
        if(true /*checkname*/){
            Session session = DAOSession.getNewSession();
            session.beginTransaction();
            DAOUserStory story = session.createQuery( "from DAOUserStory where name = ?1" , DAOUserStory.class).setParameter(1, name).uniqueResult();
            story.setName(newName);;
            session.persist(story);            
            session.getTransaction().commit();
            session.close();
            return true;
        }
        return false;}

    public static boolean updateDescription(String name,String newDescription){
        if(true /*checkname*/){
            Session session = DAOSession.getNewSession();
            session.beginTransaction();
            DAOUserStory story = session.createQuery( "from DAOUserStory where name = ?1" , DAOUserStory.class).setParameter(1, name).uniqueResult();
            story.setDescription(newDescription);
            session.persist(story);            
            session.getTransaction().commit();
            session.close();
            return true;
        }
        return false;
    }

    public static boolean updatePriority(String name,int newPriority){
        if(true /*checkname*/){
            Session session = DAOSession.getNewSession();
            session.beginTransaction();
            DAOUserStory story = session.createQuery( "from DAOUserStory where name = ?1" , DAOUserStory.class).setParameter(1, name).uniqueResult();
            story.setPriority(newPriority);
            session.persist(story);            
            session.getTransaction().commit();
            session.close();
            return true;
        }
        return false;
    }
}
