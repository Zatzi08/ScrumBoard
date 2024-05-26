package com.team3.project;

import com.team3.project.DAOService.*;
import com.team3.project.Facede.PresentationToLogic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) throws Exception {
        DAOStartService.start();
        PresentationToLogic p = PresentationToLogic.getInstance();
        if (!DAOAccountService.checkMail("T@M.com")){
            p.accountService.register("T","T@M.com", "T");
        }
        if (DAOTaskBoardService.getAll().isEmpty()){
            p.taskBoardService.createTaskBoard("Default");
        }
        DAOUserService.updateAuthorizationById(DAOUserService.getIdByMail("T@M.com"), 4);
        SpringApplication.run(ProjectApplication.class, args);
    }

}
