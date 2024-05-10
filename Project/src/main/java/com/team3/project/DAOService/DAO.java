package com.team3.project.DAOService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.team3.project.Classes.UserStory;
import com.team3.project.DAO.DAORole;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOUserStory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

class DAO {
    private static EntityManagerFactory sessionFactory;
    public static void main(String[] args) {
        try {
        setUp();
        //  String TestEmail = "TestEmail";
        //  String TestName = "TestName";
        //  String TestName2 = "TestName2";
        //  String TestPrivatDescription = "TestPrivatDescription";
        //  String TestWorkDescription = "TestWorkDescription";
        //  DAORole TestRole = new DAORole("TestRol");
        //  DAORole TestRole2 = new DAORole("TestRol2");
        //  List<DAORole> TestRols = new ArrayList<>();
        // DAOAccountService.create(TestEmail, "TestPW");
        // DAOUserService.createByEMail(TestEmail, TestName, TestPrivatDescription, TestWorkDescription, TestRols);
        /* 
        List<DAORole> list = DAOUserService.getRoleBySessionID(1);
        for (DAORole daoRole : list) {
            System.out.println(daoRole.getName());
        }
        //System.out.println(DAOUserService.getById(1).getName());
        DAORole roleDeveloper = DAORoleService.getByID(1);
        List<DAOUser> users = DAOUserService.getAllPlusRoles();

        if (users != null && !users.isEmpty()) {
            users.stream().forEach(user -> {
                user.setPassword("null");
                user.getRoles().add(roleDeveloper);
            });
            users.get(0).getRoles().add(DAORoleService.getByID(4));
            System.out.println(DAOUserService.updateUsers(users));
        } else System.out.println("Null/Empty - 555555555555555555555555555555555555555");
        /**/
        /*
        DAOUserStory userStory = DAOUserStoryService.getById(1);
        System.out.println(DAOTaskService.create("Farbe kaufen", userStory.getId()));
        /**/
        System.out.println(DAOTaskService.getListByUserStoryId(1));

        //Usertest();   
        tearDown();    
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static <T> List<T> getList(Class<T> daoClass, String fetchName) {
        EntityManager entityManager = sessionFactory.createEntityManager();
        entityManager.getTransaction().begin();
        String query = "SELECT item FROM " + daoClass.getName() + " AS item JOIN FETCH item." + fetchName;
        List<T> retrieve;
        try {
            retrieve = entityManager.createQuery(query, daoClass).getResultList();
        } catch (Exception e) {
            retrieve = null;
        } finally {
            entityManager.close();
        }
        return retrieve;
    }

    private static int createSessionId() {
        Random random = new Random();
        return random.nextInt(10000000, 100000000);
    }

    protected static void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
              .configure("DB/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
              .build();
        try {
           sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
           // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
           // so destroy it manually.
           StandardServiceRegistryBuilder.destroy( registry );
           System.out.println(e);
           System.out.println("setup fehlgeschlagen 666666666666666666666666666666666666666666666666666");
        }
     } 
    protected static void tearDown() throws Exception {
        if ( sessionFactory != null ) {
           sessionFactory.close();
        }
    }
}
