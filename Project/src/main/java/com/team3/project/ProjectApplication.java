package com.team3.project;

import com.team3.project.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@SpringBootApplication
@Controller
public class ProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @Autowired
    private LoginService loginService;
    // Start Page (static)
    // TODO: ersetze index.html mit Login Page
    @RequestMapping(value = "/")
    public String index() {
        return "index.html";
    }

    // TODO: implement Login.check.Database(UName,Pw) in service
    // TODO: create Template for User Page
    // FIXME: Ersetze ?myName (GET) Typ von Request durch POST Typ

    @GetMapping("/hello")
    @ResponseBody
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        return loginService.LoginCheck(name, "Baum") ? String.format("Hello %s!", name) : String.format("Wrong Username or Passwort");
    }
}
