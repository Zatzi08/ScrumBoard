package com.team3.project.Controller;

import com.team3.project.Classes.Enumerations;
import com.team3.project.Classes.UserStory;
import com.team3.project.DAOService.DAOUserStoryService;
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

    /* Author: Henry L. Freyschmidt
     * Revisited:
     * Funktion:
     * Grund:
     * UserStory/Task-ID: /
     */
    @RequestMapping(value ="/Login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "EMail", required = true) String EMail,
                        @RequestParam(value = "Passwort", required = true) String Passwort) {
        try{
            if(presentationToLogic.accountService.checkMail(EMail) &&
                    presentationToLogic.accountService.login(EMail, Passwort)) return "redirect:/ProjectManager";
        } catch (Exception e){
            e.printStackTrace();
        }
            return "redirect:/";
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Weiterleiten zu neues Passwort-Seite
     * Grund: Möglichkeit neues Passwort einzugeben
     * UserStory/Task-ID: /
     */
    @RequestMapping(value = "/neuesPasswortPage", method = RequestMethod.POST)
    public ModelAndView neuesPasswortPage(@RequestParam(value = "EMail", required = true) String EMail){
        if(presentationToLogic.accountService.checkMail(EMail)) return new ModelAndView("neuesPasswort").addObject("Mail", EMail);
        return new ModelAndView("index");
    }


    /* Author: Lucas Krüger
     * Revisited: Henry L. Freyschmidt
     * Funktion: Übergabe eines neuen Passworts an Datenbank
     * Grund: neusetzen eines Passworts
     * UserStory/Task-ID: /
     */
    @RequestMapping(value = "/neuesPasswort", method = RequestMethod.POST)
    @ResponseBody
    public String neuesPasswort(@RequestParam(value = "Passwort", required = true) String Passwort,
                                @RequestParam(value = "EMail", required = true)String EMail){
        // TODO: Mail mit Verifizierungscode + neuen Link für zurücksetzen
        try {
            presentationToLogic.accountService.resetPasswort(EMail, Passwort);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Übergabe von Userdaten an Datenbank und Rückgabe des Projektmanagers oder eines Fail
     * Grund: Speichern von neues Userdaten
     * UserStory/Task-ID: A2.B1
     */
    @RequestMapping(value = "/Register", method = RequestMethod.POST)
    public ModelAndView Register(@RequestParam(value = "Username", required = true) String Username,
                                 @RequestParam(value = "EMail", required = true) String EMail,
                                 @RequestParam(value = "Passwort", required = true) String Passwort){
        if(!presentationToLogic.accountService.checkMail(EMail)) {
            try {
                if(presentationToLogic.accountService.register( Username,EMail,Passwort))
                    return new ModelAndView("index");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("register");
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Übergabe von Daten als UserStory.class an Logic/Data
     * Grund: Übergeben von UserStorys
     * UserStory/Task-ID:
     */
    @RequestMapping(value = "/addStory", method = RequestMethod.POST)
    public String addStory(@RequestParam(value = "name", required = true) String name,
                           @RequestParam(value = "description", required = true) String Desc,
                           @RequestParam(value = "priority", required = false) int prio,
                           @RequestParam(value = "id", required = true, defaultValue = "-1") int id){
        Enumerations prior = new Enumerations();
        UserStory Story = new UserStory(name, Desc, prior.IntToPriority(prio),id);
        try {
            presentationToLogic.userStoryService.addUserStory(Story);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/ProjectManager";
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Anzeigen aller Userstorys
     * Grund: /
     * UserStory/Task-ID: U1.B1, U3.B1, U4.B1, U5.B1, U5.B2 // Todo: Fix
     */
    @RequestMapping("/ProjectManager")
    private ModelAndView ProjectManager(){
        ModelAndView modelAndView = new ModelAndView("projectManager");
        modelAndView.addObject("Storys", presentationToLogic.userStoryService.getAllUserStorys());
        return modelAndView;
    }

    /* Author: Lucas Krüger
     * Revisited:
     * Funktion: Beispiel für Websocket Integration
     * Grund: Erkannte Änderungen in DB an Seite Weiterleiten
     * UserStory/Task-ID: /
     */
    /*@RequestMapping("/socket")
    public ModelAndView socket(){
        ModelAndView modelAndView = new ModelAndView("Tasklist");
        modelAndView.addObject("Story", presentationToLogic.userStoryService.getUserStoryT());
        return modelAndView;
    }*/
}
