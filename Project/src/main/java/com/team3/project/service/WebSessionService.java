package com.team3.project.service;

import com.team3.project.DAO.DAOUser;
import com.team3.project.DAOService.DAOUserService;
import org.springframework.stereotype.Service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Random;


@Service
public class WebSessionService {
    private final DateFormat dformat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private final long sessionTimeOffset = 60 * 60 * 1000;
    private final String MasterID = "EAIFPH8746531";

    public boolean verify(String sessionId) throws Exception {
        if (sessionId == null) throw new Exception("Null ID");
        return sessionId.equals(MasterID) || checkSessionID(sessionId);
    }

    public String getSessionID(String email) throws Exception {
        if( email == null) throw new Exception("Null Email");
        DAOUser user = DAOUserService.getById(DAOUserService.getIdByMail(email));
        Date currentDate = new Date();
        if (user == null) throw new Exception("User not found");
        if (user.getSessionId() == null){
            String sessionID = generatCode(0);
              //TODO: Make SessionIDs greater again / lass die Schleife in nem Error abbrechen nach x Wiederholungen
            while (DAOUserService.checkSessionId(sessionID)){
                sessionID = generatCode(0);
            }
            DAOUserService.updateSessionIdById(user.getId(),sessionID, dformat.format(currentDate));
            return sessionID;
        } else {
            DAOUserService.updateSessionIdById(user.getId(),user.getSessionId(), dformat.format(currentDate));
            return user.getSessionId();
        }
    }

    private boolean checkSessionID(String sessionID) throws Exception {
        if( sessionID == null) throw new Exception("Null SessionID");
        DAOUser user = DAOUserService.getBySessionId(sessionID);
        if (user == null) throw new Exception("User not found");

        Date d = dformat.parse(user.getSessionDate());
        d.setTime(d.getTime() + sessionTimeOffset);
        Date currentDate = new Date();
        if (d.after(currentDate)){
            DAOUserService.updateSessionIdById(user.getId(),sessionID, dformat.format(currentDate));
            return true;
        }
        return false;
    }


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

    public String generatCode(int i) throws Exception {
        return switch (i) {
            case 0 -> SessionID();
            case 1 -> "code:" + ResetCode();
            default -> throw new Exception("No valid Code Parameter");
        };
    }

    public boolean checkCode(String code) {
        return true;
    }
}

