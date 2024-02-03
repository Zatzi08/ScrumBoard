package com.team3.project;

import com.team3.project.service.AccountService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@SpringBootApplication
@Controller
public class ProjectApplication {

    public ProjectApplication(AccountService accountService) {
        this.accountService = accountService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    private final AccountService accountService;

    // TODO: IOExeptions
    // Start Seite (static)
    // TODO: ersetze index.html mit Login Page
    @RequestMapping(value = "/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    // TODO: implement Login.check.Database(UName,Pw) in service
    // TODO: create Template for User Page

    @RequestMapping(value ="/Login", method = RequestMethod.POST)
    @ResponseBody
    public String Login(String Username, String Passwort) {
        return accountService.LoginCheck(Username, Passwort) ? String.format("Hello %s! Dein PW: %s!!", Username, Passwort) : "Wrong Username or Passwort";
    }
    @RequestMapping(value = "/Register", method = RequestMethod.POST)
    @ResponseBody
    public String Register(String EMail, String Passwort){
        return accountService.CreateAccount(EMail,Passwort) ? "Moin, du bis registriert!!" : "Deine E-Mail ist bereits mit einem Account verbunden." ;
    }

}
