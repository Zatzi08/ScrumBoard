package com.team3.project.DAOService;

import java.util.List;

import org.hibernate.Session;

import com.team3.project.DAO.DAOUserStory;

public class DAOUserStoryService {

    public static DAOUserStory getByID(int id){
        Session session = DAOSession.getNewSession();
        session.beginTransaction();
        DAOUserStory story = session.createQuery( "from DAOUserStory where id = ?1" , DAOUserStory.class).setParameter(1, id).uniqueResult();
        session.close();
        return story;
    }

    public static DAOUserStory getByName(String name){
        Session session = DAOSession.getNewSession();
        session.beginTransaction();
        DAOUserStory story = session.createQuery( "from DAOUserStory where name = ?1" , DAOUserStory.class).setParameter(1, name).uniqueResult();
        session.close();
        return story;
    }

    public static List<DAOUserStory> getAll(){
        Session session = DAOSession.getNewSession();
        session.beginTransaction();
        List<DAOUserStory> user = session.createQuery( "from DAOUserStory" , DAOUserStory.class).list();
        if (!user.isEmpty()) {
            session.close();
            return user;
        }
        session.close();
        return null;
    }

    public static boolean create(String name,String description,int priority){
        DAOUserStory tosave = new DAOUserStory( name, description, priority);
        if(!checkName(name)){
            Session session = DAOSession.getNewSession();
            session.beginTransaction();
            session.persist(tosave);
            session.getTransaction().commit();
            session.close();
            return true;
        }
        return false;
    }

    public static boolean delete(int id){
        if(checkId(id)){
            Session session = DAOSession.getNewSession();
            session.beginTransaction();
            DAOUserStory story = session.createQuery( "from DAOUserStory where id = ?1" , DAOUserStory.class).setParameter(1, id).uniqueResult();
            session.remove(story);
            session.getTransaction().commit();
            session.close();
            return true;
        }
    return false;
    }

    public static boolean updateName(int id,String newName){
        if(checkId(id) && !checkName(newName)){
            Session session = DAOSession.getNewSession();
            session.beginTransaction();
            DAOUserStory story = session.createQuery( "from DAOUserStory where id = ?1" , DAOUserStory.class).setParameter(1, id).uniqueResult();
            story.setName(newName);;
            session.persist(story);            
            session.getTransaction().commit();
            session.close();
            return true;
        }
        return false;}

    public static boolean updateDescription(int id,String newDescription){
        if(checkId(id)){
            Session session = DAOSession.getNewSession();
            session.beginTransaction();
            DAOUserStory story = session.createQuery( "from DAOUserStory where id = ?1" , DAOUserStory.class).setParameter(1, id).uniqueResult();
            story.setDescription(newDescription);
            session.persist(story);            
            session.getTransaction().commit();
            session.close();
            return true;
        }
        return false;
    }

    public static boolean updatePriority(int id,int newPriority){
        if(checkId(id)){
            Session session = DAOSession.getNewSession();
            session.beginTransaction();
            DAOUserStory story = session.createQuery( "from DAOUserStory where id = ?1" , DAOUserStory.class).setParameter(1, id).uniqueResult();
            story.setPriority(newPriority);
            session.persist(story);            
            session.getTransaction().commit();
            session.close();
            return true;
        }
        return false;
    }
    public static boolean checkId(int id){
        Session session = DAOSession.getNewSession();
        session.beginTransaction();
        DAOUserStory story = session.createQuery( "from DAOUserStory where id = ?1" , DAOUserStory.class).setParameter(1, id).uniqueResult();
        if (story == null) {
            session.close();
            return false;
        }
        session.close();
        return true;
    }
    public static boolean checkName(String name){
        Session session = DAOSession.getNewSession();
        session.beginTransaction();
        DAOUserStory story = session.createQuery( "from DAOUserStory where name = ?1" , DAOUserStory.class).setParameter(1, name).uniqueResult();
        if (story == null) {
            session.close();
            return false;
        }
        session.close();
        return true;
    }
}
