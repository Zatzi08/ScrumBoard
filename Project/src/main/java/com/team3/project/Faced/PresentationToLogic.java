package com.team3.project.Faced;

import com.team3.project.Controller.WebController;
import com.team3.project.service.*;

import java.util.Random;


public class PresentationToLogic {
    public final AccountService accountService;
    public final TaskService taskService;
    public final UserStoryService userStoryService;
    public final WebSessionService webSessionService;
    private static PresentationToLogic instance;
    public TaskBoardService taskBoardService;

    private PresentationToLogic(){
        this.accountService = new AccountService();
        this.userStoryService = new UserStoryService();
        this.taskService = new TaskService();
        this.webSessionService = new WebSessionService();
        this.taskBoardService = new TaskBoardService();
    }

    public static PresentationToLogic getInstance(){
        if(PresentationToLogic.instance == null) PresentationToLogic.instance = new PresentationToLogic();
        return PresentationToLogic.instance;
    }

    public boolean checkCode(String code) throws Exception {
        if (code == null) throw new Exception("Null Code");
        return false; // TODO: Implement Service, wenn DB bereit
    }

    // TODO: maybe löschen, wenn wir das von der DB handeln lassen (wäre smarter)
    private String SessionID(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 57; // numeral '9'
        int targetStringLength = 8;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    private String ResetCode(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 12;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public String GeneratCode(int i) throws Exception {
        return switch (i) {
            case 0 -> SessionID();
            case 1 -> "code:" + ResetCode();
            default -> throw new Exception("No valid Code Parameter");
        };
    }
}
