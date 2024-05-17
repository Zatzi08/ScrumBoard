package com.team3.project.Controller;

import com.team3.project.Classes.Profile;
import com.team3.project.Classes.Task;
import com.team3.project.Classes.UserStory;
import com.team3.project.Facede.PresentationToLogic;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WebController {
    public WebController() {
        this.presentationToLogic = PresentationToLogic.getInstance();
    }
    private final PresentationToLogic presentationToLogic;
    private final String MasterID = "EAIFPH8746531";

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

    @RequestMapping("/Error")
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
    public ModelAndView neuesPasswortPage(@RequestParam(value = "EMail", required = true) String EMail) {
        try {
            if (presentationToLogic.accountService.checkMail(EMail)) {
                return new ModelAndView("neuesPasswort")
                        .addObject("Mail", EMail)
                        .addObject("Code", presentationToLogic.webSessionService.generatCode(1));
            }
        } catch(Exception e){
            e.printStackTrace();
            return error(e);
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
    public ModelAndView neuesPasswort(@RequestParam(value = "Passwort", required = true) String Passwort,
                                @RequestParam(value = "EMail", required = true)String EMail){
        try {
            presentationToLogic.accountService.resetPasswort(EMail, Passwort);
        } catch (Exception e) {
            e.printStackTrace();
            return error(e);
        }
        return index();
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
                if(!presentationToLogic.accountService.checkMail(EMail)) {
                    if (presentationToLogic.accountService.register(Username, EMail, Passwort))
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
            if( presentationToLogic.accountService.login(EMail, Passwort)) {
                String id = presentationToLogic.webSessionService.getSessionID(EMail);
                return ProjectManager(id);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return index();
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Übergabe von Daten für UserStory an Logic/Data
     * Grund: Übergeben von UserStorys
     * UserStory/Task-ID:
     */
    @RequestMapping(value = "/saveStory", method = RequestMethod.POST)
    public ResponseEntity<HttpStatus> saveStory(@RequestHeader(value = "sessionID", required = true) String sessionID,
                                                @RequestBody(required = true) UserStory userStory){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)) {
                if (presentationToLogic.accountService.getAuthority(sessionID)  >= 2){
                    presentationToLogic.userStoryService.saveUserStory(userStory);
                    return new ResponseEntity<HttpStatus>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Anzeigen aller Userstorys
     * Grund: /
     * UserStory/Task-ID: U1.B1 (UserStory)
     */
    @RequestMapping(value = "/ProjectManager")
    private ModelAndView ProjectManager(@RequestParam(value = "sessionID", required = true) String sessionID){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)) {
                ModelAndView modelAndView = new ModelAndView("projectManager");
                modelAndView.addObject("Storys", presentationToLogic.userStoryService.getAllUserStorys())
                        .addObject("SessionId", sessionID);
                return modelAndView;
            }
        } catch (Exception e){
            e.printStackTrace();
            return error(e);
        }
        return index();
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Anzeigen des ResetCodes
     * Grund: /
     * UserStory/Task-ID: A4.B1
     */
    @RequestMapping(value = "/RequestResetCode", method = RequestMethod.POST)
    private ModelAndView RequestRestCode(@RequestParam(value = "email",required = true) String email){
        if (presentationToLogic.accountService.checkMail(email)) {
            try {
                String code = presentationToLogic.webSessionService.generatCode(1);
                ModelAndView modelAndView = new ModelAndView("neuesPasswort")
                        .addObject("Code", code);
                return modelAndView;
            } catch (Exception e){
                e.printStackTrace();
                return error(e);
            }
        }
        return new ModelAndView("passwortForgot")
                .addObject("error", true)
                .addObject("EMail", email);
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
            if (presentationToLogic.accountService.checkMail(email) && presentationToLogic.webSessionService.checkCode(code)) {
                presentationToLogic.accountService.resetPasswort(email, passwort);
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
     * UserStory/Task-ID: A4.B3
     */
    @RequestMapping(value = "/SendMail", method = RequestMethod.POST)
    private ResponseEntity SendMail(@RequestHeader(value = "sessionID", required = true) String sessionID,
                                    @RequestParam(value = "senderEmail",required = true) String from,
                                    @RequestParam(value = "recieverEmail",required = true)String to,
                                    @RequestParam(value = "text",required = true) String text){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)) {
                System.out.println("From:" + from + "\nTo:" + to + "\nText:\n" + text);
            }
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Ausgeben einer ProfilPage by ID
     * Grund: /
     * UserStory/Task-ID: P1.B1
     */
    @RequestMapping(value = "/getProfilePage")
    private ModelAndView getProfilePage(@RequestParam(value = "sessionID", required = true) String sessionID){
        try{
            if (presentationToLogic.webSessionService.verify(sessionID)){
                Profile user = presentationToLogic.accountService.getProfileByID(sessionID);
                if (user != null) return new ModelAndView("profil")
                        .addObject("User" , user)
                        .addObject("SessionId", sessionID);
            }
        } catch (Exception e){
            e.printStackTrace();
            return error(e);
        }
        return index();
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: Ausgeben aller Profile nach E-Mail
     * Grund: /
     * UserStory/Task-ID: P1.B1
     */
    @RequestMapping(value = "/getProfilePageByEmail", method = RequestMethod.POST)
    private ModelAndView getProfilePageByName(@RequestParam(value = "sessionID", required = true) String sessionID,
                                              @RequestParam(value = "email",required = true) String email){
        try{
            if (presentationToLogic.webSessionService.verify(sessionID)){
                Profile user = presentationToLogic.accountService.getProfileByEmail(email);
                if (user != null) return new ModelAndView("profil")
                        .addObject("User", user)
                        .addObject("SessionId", sessionID);
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
    @RequestMapping(value = "/saveUserData", method = RequestMethod.POST)
    private ModelAndView saveUserData(@RequestHeader(value = "sessionID", required = true) String sessionID,
                                @RequestParam(value = "uName", required = true) String name,
                                @RequestParam(value = "rolle",required = false, defaultValue = "-1") String rolle,
                                @RequestParam(value = "wDesc",required = true) String wDesc,
                                @RequestParam(value = "pDesc",required = true) String pDesc){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                presentationToLogic.accountService.savePublicData(sessionID, name, rolle, wDesc, pDesc);
            }
        } catch (Exception e){
            e.printStackTrace();
            return error(e);
        }
        return getProfilePage(sessionID);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion:
     * Grund: /
     * UserStory/Task-ID: T1.B1
     */
    @RequestMapping(value = "/getAllTask")
    private ModelAndView AllTask(@RequestParam(value = "sessionID", required = true) String sessionID){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                return new ModelAndView("projectManager-Tasks")
                        .addObject("Tasks", presentationToLogic.taskService.getAllTask())
                        .addObject("UserStory", presentationToLogic.userStoryService.getAllUserStorys())
                        .addObject("SessionId", sessionID);
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
     * UserStory/Task-ID: T1.B1
     */
    @RequestMapping(value = "/getTaskByTLID", method = RequestMethod.POST)
    private ModelAndView getTaskByTLID(@RequestHeader(value = "sessionID", required = true) String sessionID,
                          @RequestParam(value = "TLID", required = true, defaultValue = "-1") int TLId){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                return new ModelAndView("projectManager-Tasks")
                        .addObject("SessionId",sessionID)
                        .addObject("Tasks", presentationToLogic.taskService.getAllTask()); // TODO: getAllTaskByTLID
            }
        } catch (Exception e){
            e.printStackTrace();
            return error(e);
        }
        return index();
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion:
     * Grund: /
     * UserStory/Task-ID: T1.B1
     */
    @RequestMapping(value = "/getTaskByUSID")
    private ModelAndView getTaskByUSID(@RequestParam(value = "sessionID", required = true) String sessionID,
                                       @RequestParam(value = "USID", required = true, defaultValue = "-1") int USId){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                return new ModelAndView("projectManager-TasksZuUserstory")
                        .addObject("SessionId",sessionID)
                        .addObject("Tasks", presentationToLogic.taskService.getTasksbyUSID(USId))
                        .addObject("StoryName", presentationToLogic.userStoryService.getUserStory(USId).getName())
                        .addObject("UserStory", presentationToLogic.userStoryService.getAllUserStorys());
            }
        } catch (Exception e){
            e.printStackTrace();
            return error(e);
        }
        return index();
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion:
     * Grund: /
     * UserStory/Task-ID: T3.D2, T4.D1
     */
    // TODO: Fix tlid
    @RequestMapping(value = "/saveTask", method = RequestMethod.POST)
    private ResponseEntity<HttpStatus> saveTask(@RequestHeader(value = "sessionID", required = true) String sessionID,
                                                @RequestBody(required = true) Task task){
        try{
            if (presentationToLogic.webSessionService.verify(sessionID)){
                presentationToLogic.taskService.saveTask(task);
                return new ResponseEntity<HttpStatus>(HttpStatus.OK);
            }
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion:
     * Grund: /
     * UserStory/Task-ID: T5.B1
     */
    @RequestMapping(value = "/deleteTask", method = RequestMethod.POST)
    private ResponseEntity<HttpStatus> deleteTask(@RequestHeader(value = "sessionID", required = true) String sessionID,
                                                  @RequestParam(value = "tID", required = true, defaultValue = "-1") int tid){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                presentationToLogic.taskService.deleteTask(tid);
                return new ResponseEntity<HttpStatus>(HttpStatus.OK);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion:
     * Grund: /
     * UserStory/Task-ID: U6.B1
     */
    @RequestMapping(value = "/deleteUS", method = RequestMethod.POST)
    private ResponseEntity<HttpStatus> deleteUS(@RequestHeader(value = "sessionID", required = true) String sessionID,
                                    @RequestParam(value = "ID", required = true, defaultValue = "-1") int id){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                if (presentationToLogic.accountService.getAuthority(sessionID) >= 2){
                    presentationToLogic.userStoryService.deleteUserStoryAndLinkedTasks(id);
                    return new ResponseEntity<HttpStatus>(HttpStatus.OK);
                } else return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion:
     * Grund: /
     * UserStory/Task-ID: // Todo: Place ID
     */
    @RequestMapping(value = "/getTaskBoard", method = RequestMethod.POST)
    private ModelAndView getTaskBoard(@RequestHeader(value = "sessionID", required = true) String sessionID,
                                      @RequestParam(value = "TBID", required = true, defaultValue = "-1") int tbid){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                ModelAndView modelAndView = new ModelAndView("TaskBoard")
                        .addObject("TaskBoard",presentationToLogic.taskBoardService.getTaskBoard(tbid))
                        .addObject("SessionId", sessionID);
            }
        } catch (Exception e){
            e.printStackTrace();
            return error(e);
        }
        return index();
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

    @RequestMapping("/test")
    private ModelAndView test() {
        try {
            return new ModelAndView("test");
        } catch (Exception e){
            e.printStackTrace();
            return error(e);
        }
    }
}
