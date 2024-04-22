package com.team3.project.Controller;

import com.team3.project.Classes.Profile;
import com.team3.project.Classes.UserStory;
import com.team3.project.Faced.LogicToData;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {
    public WebController() {
        this.logicToData = LogicToData.getInstance();
    }
    private final LogicToData logicToData;
    private final String MasterID = "EAIFPH8746531";

    // TODO: IOExceptions
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

    @RequestMapping("/error")
    private ModelAndView error(Exception e){
        return new ModelAndView("Error").addObject("error",e.toString());
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
     * Funktion: Weiterleiten zu Registrieren-Seite
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
    public ModelAndView neuesPasswortPage(@RequestParam(value = "EMail", required = true) String EMail){
        if(logicToData.accountService.checkMail(EMail)) {
            return new ModelAndView("neuesPasswort").addObject("Mail", EMail);
        }
        return index();
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
            logicToData.accountService.resetPasswort(EMail, Passwort);
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
            try {
                if(!logicToData.accountService.checkMail(EMail)) {
                    if (logicToData.accountService.register(Username, EMail, Passwort))
                        return index();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return new ModelAndView("register");
    }

    /* Author: Henry L. Freyschmidt
     * Revisited:
     * Funktion:
     * Grund:
     * UserStory/Task-ID: /
     */
    @RequestMapping(value ="/Login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam(value = "EMail", required = true) String EMail,
                        @RequestParam(value = "Passwort", required = true) String Passwort) {
        try{
            String id = MasterID; // TODO: getSession, oder Session als Rückgabe von Login
            if( logicToData.accountService.login(EMail, Passwort))
                return ProjectManager(id);
        } catch (Exception e){
            e.printStackTrace();
        }
        return index();
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Übergabe von Daten als UserStory.class an Logic/Data
     * Grund: Übergeben von UserStorys
     * UserStory/Task-ID:
     */
    @RequestMapping(value = "/addStory", method = RequestMethod.POST)
    public ModelAndView addStory(@RequestParam(value = "name", required = true) String name,
                           @RequestParam(value = "description", required = true) String Desc,
                           @RequestParam(value = "priority", required = false) int prio,
                           @RequestParam(value = "id", required = true, defaultValue = "-1") int id,
                           @RequestParam(value = "SessionId", required = true) String SessionId){
        UserStory story = new UserStory(name, Desc, prio,id);

        try {
            if (logicToData.webSessionService.verify(SessionId)) {
                logicToData.userStoryService.addUserStory(story);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return ProjectManager(SessionId);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Anzeigen aller Userstorys
     * Grund: /
     * UserStory/Task-ID: U1.B1, U3.B1, U4.B1, U5.B1, U5.B2 // Todo: Fix
     */
    @RequestMapping("/ProjectManager")
    private ModelAndView ProjectManager(@RequestParam(value = "SessionId",required = true) String id){
        try {
            if (logicToData.webSessionService.verify(id)) {
                ModelAndView modelAndView = new ModelAndView("projectManager");
                modelAndView.addObject("Storys", logicToData.userStoryService.getAllUserStorys()).addObject("SessionID", id);
                return modelAndView;
            }
        } catch (Exception e){
            e.printStackTrace();
            return error(e); // TODO: kann später weg
        }
        return index();
    }

    // TODO: Delete
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    private ModelAndView test(@RequestParam(value = "SessionId") String SessionId,
                              @RequestParam(value = "description") String des){
        return error(new Exception("you got it"));
    }

    /*
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    private ResponseEntity test(){
        return new ResponseEntity(HttpStatus.OK);
    }
    */
    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Anzeigen des ResetCodes
     * Grund: /
     * UserStory/Task-ID: A4.B1
     */
    @RequestMapping(value = "/RequestResetCode", method = RequestMethod.POST)
    private ModelAndView RequestRestCode(@RequestParam(value = "email",required = true) String email){
        if (logicToData.accountService.checkMail(email)) {
            try {
                String code = logicToData.GeneratCode(1);
                ModelAndView modelAndView = new ModelAndView("neuesPasswort")
                        .addObject("Code", code);
                return modelAndView;
            } catch (Exception e){
                e.printStackTrace();
                return error(e);
            }
        }
        return new ModelAndView("passwortForgot").addObject("error", true).addObject("EMail", email);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Initialisieren des Resets eines Passworts
     * Grund: /
     * UserStory/Task-ID: A5.B1
     */
    @RequestMapping(value = "/RequestResetPasswort", method = RequestMethod.POST)
    private ModelAndView RequestRestCode(@RequestParam(value = "email",required = true) String email,
                                         @RequestParam(value = "newPasswort",required = true) String passwort,
                                         @RequestParam(value = "code",required = true) String code){
        try{
            if (logicToData.accountService.checkMail(email) && logicToData.checkCode(code)) {
                logicToData.accountService.resetPasswort(email, passwort);
            }
        } catch (Exception e){
            e.printStackTrace();
            return error(e);
        }
        return index();
    }

    // TODO: wäre sweet, wenn das hier mal wer testet sobald frontend existiert
    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: "Senden" einer Mail
     * Grund: /
     * UserStory/Task-ID: // Todo: Place ID
     */
    @RequestMapping(value = "/SendMail", method = RequestMethod.POST)
    private void SendMail(@RequestParam(value = "senderEmail",required = true) String from,
                          @RequestParam(value = "recieverEmail",required = true)String to,
                          @RequestParam(value = "text",required = true) String text,
                          @RequestParam(value = "SessionId",required = true) String SessionId){
        try {
            if (logicToData.webSessionService.verify(SessionId)) {
                System.out.println("From:" + from + "\nTo:" + to + "\nText:\n" + text);
            }
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Ausgeben einer ProfilPage by ID
     * Grund: /
     * UserStory/Task-ID: P1.B1
     */
    @RequestMapping(value = "/GetProfile", method = RequestMethod.POST)
    private ModelAndView GetProfilePage(@RequestParam(value = "SessionId",required = true) String SessionId){
        try{
            if (logicToData.webSessionService.verify(SessionId)){
                Profile user = logicToData.accountService.getProfileByID(SessionId);
                if (user != null) return new ModelAndView("profil").addObject("User", user);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ModelAndView("Error").addObject("error", e.toString());
        }
        return new ModelAndView("projectManager").addObject("Storys", logicToData.userStoryService.getAllUserStorys()).addObject("SessionId",SessionId);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Ausgeben aller Profile gleichen Namens
     * Grund: /
     * UserStory/Task-ID: P1.B1
     */
    @RequestMapping(value = "/GetProfileByName", method = RequestMethod.POST)
    private ModelAndView GetProfilePageByName(@RequestParam(value = "name",required = true) String name,
                                              @RequestParam(value = "SessionId",required = true) String SessionId){
        try{
            if (logicToData.webSessionService.verify(SessionId)){
                Profile user = logicToData.accountService.getProfileByName(name);
                if (user != null) return new ModelAndView("profil").addObject("User", user);
            }
        } catch (Exception e){
            e.printStackTrace();
            return error(e);
        }
        return index();
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Speicherung/Anpassung von User-Daten
     * Grund: /
     * UserStory/Task-ID: P2.B1
     */
    @RequestMapping(value = "SaveUserData", method = RequestMethod.POST)
    private ModelAndView SaveUserData(@RequestParam(value = "SessionId",required = true) String SessionId,
                                @RequestParam(value = "uName", required = true) String name,
                                @RequestParam(value = "rolle",required = true) String rolle,
                                @RequestParam(value = "uDesc",required = true) String uDesc,
                                @RequestParam(value = "pDesc",required = true) String pDesc){
        try {
            if (logicToData.webSessionService.verify(SessionId)){
                logicToData.accountService.SavePublicData(SessionId, name, rolle, uDesc, pDesc);
            }
        } catch (Exception e){
            e.printStackTrace();
            return error(e);
        }
        return GetProfilePage(SessionId);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion:
     * Grund: /
     * UserStory/Task-ID: T1.B1
     */
    @RequestMapping(value = "/GetAllTask", method = RequestMethod.POST)
    private ModelAndView AllTask(@RequestParam(value = "SessionId",required = true) String SessionId){
        try {
            if (logicToData.webSessionService.verify(SessionId)){
                ModelAndView modelAndView = new ModelAndView("Tasklist");
                modelAndView.addObject("Tasks", logicToData.taskService.getAllTask()).addObject("SessionId", SessionId);
                return modelAndView;
            }
        } catch (Exception e){
            return error(e);
        }
        return index();
    }


    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion:
     * Grund: /
     * UserStory/Task-ID: // Todo: Place ID
     */


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
