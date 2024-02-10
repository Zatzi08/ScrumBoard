package com.team3.project.DAOService;

import java.util.List;
import org.hibernate.Session;

import com.team3.project.DAO.DAOAccount;

public class DAOAccountService {
    
    public static boolean checkmail(String totest){
        Session session = DAOSession.getNewSession();
        session.beginTransaction();
        List<DAOAccount> user = session.createQuery( "from DAOAccount where email = ?1" , DAOAccount.class).setParameter(1, totest).list();
        if (user.isEmpty()) {
            session.close();
            return false;
        }
        session.close();
        return true;
    }

    public static boolean LoginCheck(String mail,String totest){
        Session session = DAOSession.getNewSession();
        session.beginTransaction();
        DAOAccount user = session.createQuery( "from DAOAccount where email = ?1" , DAOAccount.class).setParameter(1, mail).uniqueResult();
        if (user!=null && user.getPassword().equals(totest)) {
            session.close();
            return true;
        }
        session.close();
        return false;
    }
    public static boolean createAccount(String email, String password){
        DAOAccount newaAccount = new DAOAccount(email, password);
        if(!checkmail(email)){
            Session session = DAOSession.getNewSession();
            session.beginTransaction();
            session.persist(newaAccount);
            session.getTransaction().commit();
            session.close();
            return true;
        }
        return false;
    }

    public static boolean updatePassword(String email, String newpassword){
        if(checkmail(email)){
            Session session = DAOSession.getNewSession();
            session.beginTransaction();
            DAOAccount user = session.createQuery( "from DAOAccount where email = ?1" , DAOAccount.class).setParameter(1, email).uniqueResult();
            user.setPassword(newpassword);
            session.persist(user);            
            session.getTransaction().commit();
            session.close();
            return true;
        }
    return false;
    }
    
    public static boolean deleteAccount(String email){
        if(checkmail(email)){
            Session session = DAOSession.getNewSession();
            session.beginTransaction();
            DAOAccount user = session.createQuery( "from DAOAccount where email = ?1" , DAOAccount.class).setParameter(1, email).uniqueResult();
            session.remove(user);
            session.getTransaction().commit();
            session.close();
            return true;
        }
    return false;
    }
}

//"select t from DAOAccount t"