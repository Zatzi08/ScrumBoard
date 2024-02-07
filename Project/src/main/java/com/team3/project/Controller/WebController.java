package com.team3.project.Controller;

import com.team3.project.Interface.PresentationToLogic;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {
    public WebController() {
        this.presentationToLogic = PresentationToLogic.getInstance();
    }
    private final PresentationToLogic presentationToLogic;

    // TODO: IOExeptions
    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Laden der Login-Page
     * Grund: /
     * UserStory/Task-ID: A3.F1
     */
    @RequestMapping(value = "/")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Weiterleiten zu PasswortForgot Seite
     * Grund: Mittel zum Passwort zurücksetzen
     * UserStory/Task-ID: /
     */
    @RequestMapping("/PasswortForgotPage")
    public ModelAndView PasswortForgot(){
        ModelAndView modelAndView = new ModelAndView("passwortForgot");
        return modelAndView;
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Weiterleiten zu Registrieren-Seiter
     * Grund: /
     * UserStory/Task-ID: /
     */
    @RequestMapping(value = "/RegisterPage")
    public ModelAndView RegisterPage(){
        ModelAndView modelAndView = new ModelAndView("register");
        return modelAndView;
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Weiterleiten zu neues Passwort-Seite
     * Grund: Möglichkeit neues Passwort einzugeben
     * UserStory/Task-ID: /
     */
    @RequestMapping(value = "/neuesPasswortPage", method = RequestMethod.POST)
    public ModelAndView neuesPasswortPage(String EMail){
        ModelAndView modelAndView = new ModelAndView("neuesPasswort");
        modelAndView.addObject("Mail", EMail);
        return modelAndView;
    }


    /* Author: Lucas Krüger
     * Revisited: Henry L. Freyschmidt
     * Funktion: Übergabe eines neuen Passworts an Datenbank
     * Grund: neusetzen eines Passworts
     * UserStory/Task-ID: /
     */
    @RequestMapping(value = "/neuesPasswort", method = RequestMethod.POST)
    @ResponseBody
    public String neuesPasswort(String Passwort, String EMail){
        presentationToLogic.accountService.resetPasswort(EMail, Passwort);
        return String.format("%s : %s", EMail, Passwort);
    }

    /* Author: Henry L. Freyschmidt
     * Revisited:
     * Funktion:
     * Grund:
     * UserStory/Task-ID: /
     */
    @RequestMapping(value ="/Login", method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam(value = "Username", required = false, defaultValue = "") String Username,
                        @RequestParam(value = "Passwort", required = false, defaultValue = "") String Passwort) {
        // TODO: implement Login.check.Database(EMail,Pw) in service
        return presentationToLogic.accountService.login(Username, Passwort) ? String.format("Hello %s! Dein PW: %s!!", Username, Passwort) : "Wrong Username or Passwort";
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Übergabe von Userdaten an Datenbank und Rückgabe des Projektmanagers oder eines Fail
     * Grund: Speichern von neues Userdaten
     * UserStory/Task-ID: A2.B1
     */
    @RequestMapping(value = "/Register", method = RequestMethod.POST)
    public ModelAndView Register(@RequestParam(value = "Username", required = false, defaultValue = "") String Username,
                                 @RequestParam(value = "EMail", required = false, defaultValue = "") String EMail,
                                 @RequestParam(value = "Passwort", required = false, defaultValue = "") String Passwort){
        ModelAndView projectManager = new ModelAndView("projectManager");
        ModelAndView Fail = new ModelAndView("index");
        return presentationToLogic.accountService.register(Username,EMail,Passwort) ? projectManager : Fail;
    }

    /* Author: Lucas Krüger
     * Revisited:
     * Funktion: Manueller Test für Aussehen von Webpackes
     * Grund: Korrekte Integration von Thymeleaf nur so zu testen
     * UserStory/Task-ID: /
     */
    @RequestMapping(value = "/Preview")
    public ModelAndView Preview(){
        ModelAndView modelAndView = new ModelAndView("projectManager"); // Name für Page hier
        return modelAndView;
    }

    /* Author: Lucas Krüger
     * Revisited:
     * Funktion: Beispiel für Websocket Integration
     * Grund: Erkannte Änderungen in DB an Seite Weiterleiten
     * UserStory/Task-ID: /
     */
    @RequestMapping("/socket")
    public ModelAndView socket(){
        ModelAndView modelAndView = new ModelAndView("Tasklist");
        modelAndView.addObject("Story", presentationToLogic.userStoryService.getUserStoryT());
        return modelAndView;
    }
}
