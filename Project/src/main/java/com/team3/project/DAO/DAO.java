package com.team3.project.DAO;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.team3.project.DAOService.DAOAccountService;

import jakarta.persistence.EntityManagerFactory;

class DAO {
    private static EntityManagerFactory sessionFactory;
    public static void main(String[] args) {
        try {
        setUp();
        // System.out.println(DAOAccountService.checkmail("temp")+"\n");
        // System.out.println(DAOAccountService.LoginCheck("temp","password"));
        // System.out.println(DAOAccountService.createAccount("newDave2", "pass"));
        // System.out.println(DAOAccountService.updatePassword("newDave2", "test"));
        // System.out.println(DAOAccountService.deleteAccount("max@web.de"));
        //System.out.println(DAOUserStoryService.create("testName", "null", 0));
        //System.out.println(DAOUserService.getByID(1).getRoles().size());
        
        /* List<DAOEvent> list = DAOEventService.getAll();
        for (DAOEvent test : list) {
            java.util.Date testing = new SimpleDateFormat("dd.MM.yyyy").parse(test.getTimeFrom());
            System.out.println(test.getTimeFrom());
        } */
        
        //System.out.println(list.get(0).getName()+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        /* List<DAOUser> list = DAOUserService.getAll();
        for (DAOUser test : list) {
            System.out.println(test.getName());
            System.out.println(test.getRoles());
            System.out.println("---");
        } */
        /* List<DAORole> list = DAOUserService.getRoleBySessionID(1);
        for (DAORole daoRole : list) {
            System.out.println(daoRole.getName());
        } */
        //System.out.println(DAOUserService.getById(1).getName());
        System.out.println(DAOAccountService.checkMail("Peter"));
        System.out.println(DAOAccountService.checkMail("test@this.com"));

        //Usertest();   
        tearDown();    
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // private static void Usertest(){
    //     Session session = sessionFactory.openSession();
    //     session.beginTransaction();
    //     DAOAccount account = new DAOAccount("temp2","password");
    //     session.persist(account);
    //     session.getTransaction().commit();
    //     List<DAOAccount> result = session.createQuery( "select t from DAOAccount t" , DAOAccount.class).list();
    //     for ( DAOAccount test : result) {
    //         System.out.println( "account (email:" + test.getEmail() + " pw:"+ test.getPassword() +")");
    //      }
    //      session.close();
    // }
    

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
