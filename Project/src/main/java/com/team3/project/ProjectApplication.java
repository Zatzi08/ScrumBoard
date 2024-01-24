package com.team3.project;

import com.team3.project.service.AccountService;
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
    private AccountService accountService;

    // Start Seite (static)
    // TODO: ersetze index.html mit Login Page
    @RequestMapping(value = "/")
    public String index() {
        return "index.html";
    }

    // TODO: implement Login.check.Database(UName,Pw) in service
    // TODO: create Template for User Page
    // FIXME: Ersetze ?myName (GET) Typ von Request durch POST Typ

    @RequestMapping(value ="/Login", method = RequestMethod.POST)
    @ResponseBody
    public String Login(String Username, String Passwort) {
        return accountService.LoginCheck(Username, Passwort) ? String.format("Hello %s! Dein PW: %s!!", Username, Passwort) : String.format("Wrong Username or Passwort");
    }
}
