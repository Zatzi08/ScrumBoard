package com.team3.project.service;

//import com.team3.project.DAOService.DAOSession;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;


@Service
public class WebSessionService {
    private final DateFormat dformat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private final String MasterID = "EAIFPH8746531";
    public boolean verify(String id) throws Exception {
        if (id == null) throw new Exception("Null ID");
        return id.equals(MasterID) ? true : false; // TODO: statt false DAOSessionIDCheck
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

