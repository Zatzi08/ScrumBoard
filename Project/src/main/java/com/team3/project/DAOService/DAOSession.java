package com.team3.project.DAOService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

final class DAOSession{
    private static SessionFactory sessionFactory;

    private static SessionFactory getsessionFactory(){
        if(sessionFactory==null){
            try {
                setUp();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return sessionFactory;
    }
    
    static Session getNewSession(){
        return getsessionFactory().openSession();
    }

    private static void setUp() throws Exception {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure("DB/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
            .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
        StandardServiceRegistryBuilder.destroy( registry );
        System.out.println(e);
        System.out.println("setup fehlgeschlagen 666666666666666666666666666666666666666666666666666");
        }
    } 
    static void tearDown() throws Exception {
        if ( sessionFactory != null ) {
        sessionFactory.close();
        }
    }
}