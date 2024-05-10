package com.team3.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.team3.project.DAOService.DAOStartService;


@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) {
        DAOStartService.start();
        SpringApplication.run(ProjectApplication.class, args);
    }

}
