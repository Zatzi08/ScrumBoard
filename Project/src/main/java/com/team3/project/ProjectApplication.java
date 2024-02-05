package com.team3.project;

import com.team3.project.service.AccountService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;


@SpringBootApplication
@Controller
public class ProjectApplication {

    public ProjectApplication(AccountService accountService) {
        this.accountService = accountService;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectApplication.class, args);
    }

    @Autowired
    private final AccountService accountService;

    // TODO: IOExeptions
    // Start Seite (static)
    // TODO: ersetze index.html mit Login Page
    @RequestMapping(value = "/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }


    @RequestMapping("/PasswortForgotPage")
    public ModelAndView PasswortForgot(){
        ModelAndView modelAndView = new ModelAndView("passwortForgot");
        return modelAndView;
    }


    @RequestMapping(value = "/RegisterPage")
    public ModelAndView Register(){
       ModelAndView modelAndView = new ModelAndView("register");
       return modelAndView;
    }
    // TODO: implement Login.check.Database(UName,Pw) in service
    // TODO: create Template for User Page


    @RequestMapping(value = "/neuesPasswortPage", method = RequestMethod.POST)
    public ModelAndView neuesPasswortPage(String EMail){
        ModelAndView modelAndView = new ModelAndView("neuesPasswort");
        modelAndView.addObject("Mail", EMail);
        return modelAndView;
    }


    @RequestMapping(value = "/neuesPasswort", method = RequestMethod.POST)
    @ResponseBody
    public String neuesPasswort(String Passwort, String EMail){
        accountService.resetPasswort(EMail, Passwort);
        return String.format("%s : %s", EMail, Passwort);
    }

    @RequestMapping(value ="/Login", method = RequestMethod.POST)
    @ResponseBody
    public String login(String Username, String Passwort) {
        return accountService.login(Username, Passwort) ? String.format("Hello %s! Dein PW: %s!!", Username, Passwort) : "Wrong Username or Passwort";
    }

    @RequestMapping(value = "/Register", method = RequestMethod.POST)
    @ResponseBody
    public String register(String Username, String EMail, String Passwort){
        return accountService.register(Username,EMail,Passwort) ? "Moin, du bis registriert!!" : "Deine E-Mail ist bereits mit einem Account verbunden." ;
    }

}
