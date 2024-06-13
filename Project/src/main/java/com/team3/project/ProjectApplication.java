package com.team3.project;

import com.team3.project.DAOService.*;
import com.team3.project.Facede.PresentationToLogic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) throws Exception {
        DAOStartService.start();
        SpringApplication.run(ProjectApplication.class, args);
    }

}
