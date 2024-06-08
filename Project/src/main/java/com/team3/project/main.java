package com.team3.project;

import com.team3.project.Facede.PresentationToLogic;

public class main {
    public static void main(String[] args) throws Exception {
        PresentationToLogic presentationToLogic = PresentationToLogic.getInstance();
        presentationToLogic.taskBoardService.createTaskBoard("Baum2");
    }
}
