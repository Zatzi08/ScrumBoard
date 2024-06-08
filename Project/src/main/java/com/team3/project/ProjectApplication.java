package com.team3.project;

import com.team3.project.DAOService.*;
import com.team3.project.Facede.PresentationToLogic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.LinkedList;


@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) throws Exception {
        DAOStartService.start();
        PresentationToLogic p = PresentationToLogic.getInstance();
        if (!DAOAccountService.checkMail("a@min.com")){
            p.accountService.register("admin","a@min.com", "admin");
        }
        if (DAOTaskBoardService.getAll().isEmpty()){
            p.taskBoardService.createTaskBoard("Default");
        }
        DAOUserService.updateByEMail("a@min.com", "admin", "not working", "hard working", null);
        DAOUserService.updateAuthorizationById(DAOUserService.getIdByMail("a@min.com"), 4);
        SpringApplication.run(ProjectApplication.class, args);
    }

}
