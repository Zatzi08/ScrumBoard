package com.team3.project.Tests.BaseClassesForTests;

import com.team3.project.Classes.*;
import com.team3.project.DAO.DAOTaskBoard;
import com.team3.project.DAO.DAOUser;
import com.team3.project.DAOService.DAOStartService;
import com.team3.project.DAOService.DAOTaskBoardService;
import com.team3.project.DAOService.DAOUserService;
import com.team3.project.Facede.*;

import java.util.LinkedList;
import java.util.List;

public class BaseHTTPTest extends BaseTest {
    private final static String logName = "log_HttpRequestTest.txt",
            mainTestName = "Hyper-Text-Transport-Protocol",
            testIDPrefix = "HTTP";

    protected static List<String> sessions = new LinkedList<String>();
    protected static List<Profile> profils = new LinkedList<Profile>();
    protected static List<User> users = new LinkedList<User>();
    protected static List<Account> accounts = new LinkedList<Account>();
    protected static List<Task> tasks = new LinkedList<Task>();
    protected static List<UserStory> storys = new LinkedList<UserStory>();
    protected static List<TaskBoard> boards = new LinkedList<TaskBoard>();

    public static final PresentationToLogic presentationToLogic = PresentationToLogic.getInstance();

    protected static void setup(boolean b) throws Exception {
        if (b) BaseTest.setup(logName, mainTestName);
        if (DAOUserService.checkByEmail("T@M.com")) throw new Exception("Fuck IT");
        if (boards.isEmpty()) TestDBTaskBoard();
        if (accounts.isEmpty())TestDBUser();
        if (storys.isEmpty())TestDBUserStory();
        if (tasks.isEmpty())TestDBTask();
    }
    protected static void before() {
        BaseTest.before();

    }

    protected static void after() {
        BaseTest.after(false);
    }
    protected static void tearDown() {
        BaseTest.tearDown();
    }

    protected void printWriterAddTest(String testName, String testId) {
        super.printWriterAddTest(mainTestName, testIDPrefix, testName, testId);
    }

    protected void printWriterAddFailure(String failureMessage) {
        super.printWriterAddFailure(failureMessage);
    }

    @Override
    protected void printWriterAddTest(String mainTestName, String testIDPrefix, String testName, String testId) {
        String stringBuilder = testIDPrefix +
                "-Test-" +
                testName +
                "\nTest ID: " +
                testIDPrefix +
                "." +
                testId +
                "\nDate: " +
                formatter.format(date) +
                '\n';
        printWriter.append(stringBuilder);
    }

    protected static void TestDBUser(){
        List<User> dbo = presentationToLogic.accountService.getAllUser();
        boolean reg = false;
        sessions = new LinkedList<String>();
        accounts = new LinkedList<Account>();
        if ( dbo == null || dbo.isEmpty()) {
            reg = true;
        }
        try{
            String SessionID = null;
            if (reg) presentationToLogic.accountService.register("TU1", "T1@M.com", "TPW1");
            SessionID = presentationToLogic.webSessionService.getSessionID("T1@M.com");
            presentationToLogic.accountService.savePublicData(SessionID, new Profile(0,"TU1", "T1@M.com", "pDesc1", "wDesc1",null,1));
            accounts.add(new Account("T1@M.com", "TPW1"));
            sessions.add(SessionID);
            if (reg) presentationToLogic.accountService.register("TU2", "T2@M.com", "TPW2");
            SessionID = presentationToLogic.webSessionService.getSessionID("T2@M.com");
            presentationToLogic.accountService.savePublicData(SessionID, new Profile(0,"TU2", "T2@M.com", "pDesc2", "wDesc2",null,1));
            accounts.add(new Account("T2@M.com", "TPW2"));
            sessions.add(SessionID);
            if (reg) presentationToLogic.accountService.register("TU3", "T3@M.com", "TPW1");
            SessionID = presentationToLogic.webSessionService.getSessionID("T3@M.com");
            presentationToLogic.accountService.savePublicData(SessionID, new Profile(0,"TU3", "T3@M.com", "pDesc3", "wDesc3",null, 1));
            accounts.add(new Account("T3@M.com", "TPW3"));
            sessions.add(SessionID);
            presentationToLogic.accountService.setAuthority(3,4);
            users = presentationToLogic.accountService.getAllUser();
            profils = new LinkedList<Profile>();
            for (int i = 0; i<= 2; i++){
                profils.add(presentationToLogic.accountService.getProfileByID(sessions.get(i)));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    protected static void TestDBTask(){
        List<Task> dbo = presentationToLogic.taskService.getAllTask();
        int[] ids = {1,2,3};
        if (dbo == null || dbo.isEmpty()) {
            ids = new int[]{-1, -1, -1};
        }
        try{// TODO: TBID
            presentationToLogic.taskService.saveTask(new Task(ids[0], "TT1", 1, 1, "2024-05-19T14:45", -1, -1,boards.get(0).getID()));
            presentationToLogic.taskService.saveTask(new Task(ids[1], "TT2", 1, 2, "2024-05-19T14:45", -1, -1, boards.get(0).getID()));
            presentationToLogic.taskService.saveTask(new Task(ids[2], "TT3", 1, 3, "2024-05-19T14:45", -1, -1, boards.get(0).getID()));
            tasks = presentationToLogic.taskService.getAllTask();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    protected static void TestDBUserStory(){
        List<UserStory> dbo = presentationToLogic.userStoryService.getAllUserStorys();
        int[] ids = {1,2,3};
        if (dbo == null || dbo.isEmpty()) {
            ids = new int[]{-1, -1, -1};
        }
        try{
            presentationToLogic.userStoryService.saveUserStory(new UserStory("US1","US1Desc", 1,ids[0]));
            presentationToLogic.userStoryService.saveUserStory(new UserStory("US2","US2Desc", 1,ids[1]));
            presentationToLogic.userStoryService.saveUserStory(new UserStory("US3","US2Desc", 1,ids[2]));
            storys = presentationToLogic.userStoryService.getAllUserStorys();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    protected static void TestDBTaskBoard() throws Exception {
        List<DAOTaskBoard> dtb = DAOTaskBoardService.getAll();
        if (dtb.isEmpty()) {
            presentationToLogic.taskBoardService.createTaskBoard("Test");
            dtb = DAOTaskBoardService.getAll();
        }
        boards.add(presentationToLogic.taskBoardService.toTaskBoard(dtb.get(0)));
    }
}
