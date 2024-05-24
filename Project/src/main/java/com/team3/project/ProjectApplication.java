package com.team3.project;

import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.DAOService.DAOAuthorizationService;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.Facede.PresentationToLogic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.team3.project.DAOService.DAOStartService;


@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) throws Exception {
        DAOStartService.start();
        if (!DAOAccountService.checkMail("T@M.com")){
            PresentationToLogic p = PresentationToLogic.getInstance();
            p.accountService.register("T","T@M.com", "T");
        }
        DAOUserService.updateAuthorizationById(DAOUserService.getIdByMail("T@M.com"), 4);
        SpringApplication.run(ProjectApplication.class, args);
    }

}
