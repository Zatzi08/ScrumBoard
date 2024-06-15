package com.team3.project.Controller;

import com.team3.project.Classes.Profile;
import com.team3.project.Classes.Task;
import com.team3.project.Classes.TaskBoard;
import com.team3.project.Classes.UserStory;
import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.DAOService.DAOTaskBoardService;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.Facede.PresentationToLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebController {
    public WebController(PresentationToLogic presentationToLogic) {
        this.presentationToLogic = presentationToLogic;
        try{
            if (!DAOAccountService.checkMail("T@M.com")) {
                presentationToLogic.accountService.register("T", "T@M.com", "T");
            }
            if (DAOTaskBoardService.getAll().isEmpty()) {
                presentationToLogic.taskBoardService.createTaskBoard("Default");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        DAOUserService.updateAuthorizationById(DAOUserService.getIdByMail("T@M.com"), 4);
    }
    @Autowired
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
                if (presentationToLogic.accountService.register(Username, EMail, Passwort))
                    return index();
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
            if(presentationToLogic.accountService.login(EMail, Passwort)) {
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
                if (presentationToLogic.accountService.getAuthority(sessionID)  >= 3){
                    presentationToLogic.userStoryService.saveUserStory(userStory);
                    return new ResponseEntity<HttpStatus>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
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
                        .addObject("TNames", presentationToLogic.taskService.getAllName())
                        .addObject("sessionID", sessionID);
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
        try {
            if (presentationToLogic.accountService.checkMail(email)) {
                String code = presentationToLogic.webSessionService.generatCode(1);
                ModelAndView modelAndView = new ModelAndView("neuesPasswort")
                        .addObject("Code", code);
                return modelAndView;
            }
        } catch (Exception e){
            e.printStackTrace();
            return error(e);
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
                return new ModelAndView("profil")
                        .addObject("User" , user)
                        .addObject("sessionID", sessionID)
                        .addObject("own", true);
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
    @RequestMapping(value = "/getProfilePageByEmail")
    private ModelAndView getProfilePageByName(@RequestParam(value = "sessionID", required = true) String sessionID,
                                              @RequestParam(value = "email",required = true) String email){
        try{
            if (presentationToLogic.webSessionService.verify(sessionID)){
                Profile user = presentationToLogic.accountService.getProfileByEmail(email);
                return new ModelAndView("profil")
                        .addObject("User", user)
                        .addObject("sessionID", sessionID)
                        .addObject("own", false);
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
                                      @RequestBody Profile profile){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                presentationToLogic.accountService.savePublicData(sessionID, profile);
            }
        } catch (Exception e){
            e.printStackTrace();
            return error(e);
        }
        return getProfilePage(sessionID);
    }

    @RequestMapping("/getUserList")
    private ModelAndView userList(@RequestParam(value = "sessionID",required = true) String sessionID){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                return new ModelAndView("projectManager-Nutzer")
                        .addObject("User", presentationToLogic.accountService.getAllProfiles())
                        .addObject("sessionID", sessionID)
                        .addObject("auth",presentationToLogic.accountService.getAuthority(sessionID));
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
    @RequestMapping(value = "/getAllTask")
    private ModelAndView AllTask(@RequestParam(value = "sessionID", required = true) String sessionID){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                return new ModelAndView("projectManager-Tasks")
                        .addObject("Tasks", presentationToLogic.taskService.getAllTask())
                        .addObject("UserStory", presentationToLogic.userStoryService.getAllUserStorys())
                        .addObject("sessionID", sessionID)
                        .addObject("User", presentationToLogic.accountService.getAllProfiles())
                        .addObject("TaskBoard", presentationToLogic.taskBoardService.getAllTaskBoards());
            }
        } catch (Exception e){
            return error(e);
        }
        return index();
    }

    @RequestMapping(value = "/setRealTaskTime", method = RequestMethod.POST)
    private ResponseEntity<HttpStatus> setRealTaskTime(@RequestHeader(value = "sessionID") String sessionID,
                                                       @RequestParam(value = "TID", required = true, defaultValue = "-99") int tID,
                                                       @RequestParam(value = "time", required = true, defaultValue = "-99") double time){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                presentationToLogic.taskService.setRealTimeAufwand(tID,time);
                return new ResponseEntity<HttpStatus>(HttpStatus.OK);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion:
     * Grund: /
     * UserStory/Task-ID: T1.B1
     */
    @RequestMapping(value = "/getTaskByTLID", method = RequestMethod.GET)
    private ModelAndView getTaskByTLID(@RequestParam(value = "sessionID", required = true) String sessionID,
                                       @RequestParam(value = "TLID", required = true, defaultValue = "-1") int TLId){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                return new ModelAndView("projectManager-Tasks")
                        .addObject("sessionID",sessionID)
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
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: T1.B1
     */
    @RequestMapping(value = "/getTaskByUSID")
    private ModelAndView getTaskByUSID(@RequestParam(value = "sessionID", required = true) String sessionID,
                                       @RequestParam(value = "USID", required = true, defaultValue = "-1") int USId){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                return new ModelAndView("projectManager-TasksZuUserstory")
                        .addObject("sessionID",sessionID)
                        .addObject("User", presentationToLogic.accountService.getAllProfiles())
                        .addObject("Tasks", presentationToLogic.taskService.getTasksbyUSID(USId))
                        .addObject("Story", presentationToLogic.userStoryService.getUserStory(USId))
                        .addObject("UserStory", presentationToLogic.userStoryService.getAllUserStorys())
                        .addObject("TaskBoard", presentationToLogic.taskBoardService.getAllTaskBoards());
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
    @RequestMapping(value = "/saveTask", method = RequestMethod.POST)
    private ResponseEntity<HttpStatus> saveTask(@RequestHeader(value = "sessionID", required = true) String sessionID,
                                                @RequestBody() Task task){
        try{
            if(task != null) {
                if (presentationToLogic.webSessionService.verify(sessionID)) {
                    presentationToLogic.taskService.saveTask(task);
                    return new ResponseEntity<HttpStatus>(HttpStatus.OK);
                }
            }
        } catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
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
            return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
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
                if (presentationToLogic.accountService.getAuthority(sessionID) >= 3){
                    presentationToLogic.userStoryService.deleteUserStoryAndLinkedTasks(id);
                    return new ResponseEntity<HttpStatus>(HttpStatus.OK);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: TB2.B1
     */
    @RequestMapping(value = "/getTaskBoardByID")
    private ModelAndView getTaskBoard(@RequestParam(value = "sessionID", required = true) String sessionID,
                                      @RequestParam(value = "TBID", required = true, defaultValue = "-1") int tbid){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                List<Integer> IDs = presentationToLogic.taskBoardService.getTaskBoardIDs();
                if (IDs.isEmpty()) return ProjectManager(sessionID);
                ModelAndView modelAndView = new ModelAndView("taskBoard")
                        .addObject("Board",presentationToLogic.taskBoardService.getTaskBoardByID(tbid))
                        .addObject("TBIDs", IDs)
                        .addObject("sessionID", sessionID)
                        .addObject("authority", presentationToLogic.accountService.getAuthority(sessionID))
                        .addObject("TaskBoards", presentationToLogic.taskBoardService.getAllTaskBoards());
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
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: TB2.B1
     */
    @RequestMapping(value = "/getTaskBoard")
    private ModelAndView getTaskBoard(@RequestParam(value = "sessionID", required = true) String sessionID){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                List<Integer> IDs = presentationToLogic.taskBoardService.getTaskBoardIDs();
                if (IDs.isEmpty()) return ProjectManager(sessionID);
                ModelAndView modelAndView = new ModelAndView("taskBoard")
                        .addObject("Board",presentationToLogic.taskBoardService.getTaskBoard())
                        .addObject("TBIDs", IDs)
                        .addObject("sessionID", sessionID)
                        .addObject("authority", presentationToLogic.accountService.getAuthority(sessionID))
                        .addObject("TaskBoards", presentationToLogic.taskBoardService.getAllTaskBoards());
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
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: TB9.B1
     */
    @RequestMapping(value = "/saveTaskBoard", method = RequestMethod.POST)
    private ResponseEntity<HttpStatus> saveTaskBoard(@RequestHeader(value = "sessionID", required = true) String sessionID,
                                                     @RequestBody() TaskBoard taskBoard){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                presentationToLogic.taskBoardService.saveTaskBoard(taskBoard);
                return new ResponseEntity<HttpStatus>(HttpStatus.OK);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: R1.B2
     */
    @RequestMapping("setAuthority")
    private ResponseEntity<HttpStatus> setAuthority(@RequestHeader(value = "sessionID", required = true) String sessionID,
                                                    @RequestParam(value = "USID", required = true, defaultValue = "-1") int usID,
                                                    @RequestParam(value = "Auth", required = true, defaultValue = "-1") int auth){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                if (presentationToLogic.accountService.getAuthority(sessionID) >= 3){
                    presentationToLogic.accountService.setAuthority(usID,auth);
                    return new ResponseEntity<HttpStatus>(HttpStatus.OK);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "setTaskListToTask", method = RequestMethod.GET)
    private ResponseEntity<HttpStatus> setTaskListToTask(@RequestHeader(value = "sessionID") String sessionID,
                                                         @RequestParam(value = "TID", defaultValue = "-1") int tID,
                                                         @RequestParam(value = "TLID", defaultValue = "-1") int tlID){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                presentationToLogic.taskService.setTaskList(tID,tlID);
                return new ResponseEntity<HttpStatus>(HttpStatus.OK);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
    }

    /* Author: Lucas Krüger
     * Revisited: /
     * Funktion: /
     * Grund: /
     * UserStory/Task-ID: TB11.B1
     */
    @RequestMapping(value = "/deleteTaskBoard", method = RequestMethod.POST)
    private ResponseEntity<HttpStatus> deleteTaskBoard(@RequestHeader(value = "sessionID") String sessionID,
                                                       @RequestParam(value = "TBID") int tbID){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                presentationToLogic.taskBoardService.deleteTaskBoard(tbID);
                return new ResponseEntity<HttpStatus>(HttpStatus.OK);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
    }

    @RequestMapping(value = "/setUserToTask", method = RequestMethod.POST)
    private ResponseEntity<HttpStatus> setUserToTask(@RequestHeader(value = "sessionID") String sessionID,
                                                     @RequestBody List<Integer> uIDs,
                                                     @RequestParam(value = "tID") int tID){
        try {
            if (presentationToLogic.webSessionService.verify(sessionID)){
                presentationToLogic.taskService.setUsers(tID,uIDs);
                return new ResponseEntity<HttpStatus>(HttpStatus.OK);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<HttpStatus>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.FORBIDDEN);
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
