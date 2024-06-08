package com.team3.project.Tests;

import com.team3.project.Classes.Account;
import com.team3.project.Classes.User;
import com.team3.project.Classes.UserStory;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOTaskBoard;
import com.team3.project.DAO.DAOUserStory;
import com.team3.project.DAOService.*;
import com.team3.project.Tests.BaseClassesForTests.BaseHTTPTest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTests extends BaseHTTPTest {
    @LocalServerPort
    private int port;
    private final String masterID = "EAIFPH8746531";
    @Autowired
    public TestRestTemplate restTemplate;


    @BeforeAll
    public static void BeforeAll() throws Exception {
        changeHibernateCfg(true);
        setup(true);
        if (accounts.isEmpty()) throw new RuntimeException("Fuck IT");
    }

    @BeforeEach
    public void beforeEach() {
        before();
    }

    @AfterEach
    public void afterEach() {

    }

    @AfterAll
    public static void afterAll() {
        tearDown();
        after();
        changeHibernateCfg(true);
    }


    /* Test ID: HTTP.T1
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Login
     */
    @Test
    @Tag("old")
    void LoginTest(){
        printWriterAddTest("Login", "T1");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String EMail = "Test@Mail.com";
        String Passwort = "TestFail123";
        try{
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login",HttpMethod.POST,message, String.class).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login" + "?EMail=" + accounts.get(0).getMail() + "&Passwort=" + accounts.get(0).getPassword(), HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existenter User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login" + "?EMail=" + "" + "&Passwort=" + accounts.get(0).getPassword(), HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login" + "?EMail=" + accounts.get(0).getMail() + "&Passwort=" + "", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null PW");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login" + "?EMail=" + EMail + "&Passwort=" + Passwort, HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neuer User");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T2
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Registrieren
     */
    @Test
    @Tag("old")
    void RegisterTest(){
        printWriterAddTest("Register", "T2");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String eMail = "Test@FailMail.com";

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        }  catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }
        Account account = accounts.get(0);
        User user = users.get(0);
        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register" + "?EMail=" + account.getMail() + "&Passwort=" + account.getPassword() + "&Username=" + user.getName(), HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        }  catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existenter User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register" + "?EMail=" + "" + "&Passwort=" + accounts.get(0).getPassword() + "&Username=" + users.get(0).getName(), HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register" + "?EMail=" + eMail + "&Passwort=" + "" + "&Username=" + users.get(0).getName(), HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null PW");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register" + "?EMail=" + eMail + "&Passwort=" + accounts.get(0).getPassword() + "&Username=" + "", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null User-Name");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register" + "?EMail=" + eMail + "&Passwort=" + accounts.get(0).getPassword() + "&Username=" + users.get(0).getName(), HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neuer User");
            throw new AssertionError(e);
        }

        DAOAccountService.deleteByMail(eMail);
        printWriterAddPass();
    }

    /*  Test ID: HTTP.T3
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zur neuen Passwortpage
     */
    @Test
    @Tag("old")
    void neuesPasswortPageTest(){
        printWriterAddTest("neuesPasswortPage", "T3");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String eMail = "Test@Mail.com";

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswortPage", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }
        
        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswortPage" + "?EMail=" + accounts.get(0).getMail(), HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Fail: HTTP PostRequest nicht erfolgreich - existenter User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswortPage" + "?EMail=" + "", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Fail: HTTP PostRequest nicht erfolgreich - null User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswortPage" + "?EMail=" + eMail, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Fail: HTTP PostRequest nicht erfolgreich - neuer User");
            throw new AssertionError(e);
        }
        
        printWriterAddPass();
    }

    /*  Test ID: HTTP.T4
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum neuen Passwort setzen
     */
    @Test
    @Tag("old")
    void neuesPasswortTest(){
        printWriterAddTest("neuesPasswort", "T4");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String eMail = "Test@Mail.com";
        String passwort = "TestPasswort123";
        
        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswort", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswort" + "?EMail=" + accounts.get(0).getMail() + "&Passwort=" + accounts.get(0).getPassword(), HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existenter User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswort" + "?EMail=" + "" + "&Passwort=" + accounts.get(0).getPassword(), HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswort" + "?EMail=" + accounts.get(0).getMail() + "&Passwort=" + "", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null PW");
            throw new AssertionError(e);
        }
        
        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswort" + "?EMail=" + eMail + "&Passwort=" + passwort, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neuer User");
            throw new AssertionError(e);
        }
        
        printWriterAddPass();
    }

    /*  Test ID: HTTP.T5
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum adden von neuen oder ändern bestehender USerStorys
     */
    @Test
    @Tag("old")
    void saveUserStoryTest(){
        printWriterAddTest("addUserStory", "T5");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        HttpEntity<String> message = new HttpEntity<>(body, header);
        String name = "TestStory";
        String desc = "Delete later";
        int prio = 1;
        int ID = -1;

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveStory" , HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", masterID);
        body = "{ \"ID\": \""+storys.get(0).getID()+"\", \"name\": \" "+storys.get(0).getName()+"\", \"description\": \""+storys.get(0).getDescription()+"\", \"priority\": \""+storys.get(0).getPriorityAsInt()+"\"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveStory", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existente Story - high authority");
            throw new AssertionError(e);
        }


        body = "{ \"ID\": \""+ ID +"\", \"name\": \" "+name+"\", \"description\": \""+desc+"\", \"priority\": \""+prio+"\"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveStory", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        }  catch (AssertionError e){
            printWriterAddFailure("Fail: HTTP PostRequest nicht erfolgreich - neue Story - high authority");
            throw new AssertionError(e);
        }

        header.set("sessionID", "999999999");
        body = "{ \"ID\": \""+storys.get(0).getID()+"\", \"name\": \" "+storys.get(0).getName()+"\", \"description\": \""+storys.get(0).getDescription()+"\", \"priority\": \""+storys.get(0).getPriorityAsInt()+"\"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveStory", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - invalid Session");
            throw new AssertionError(e);
        }

        header.set("sessionID", sessions.get(1));
        body = "{ \"ID\": \""+storys.get(0).getID()+"\", \"name\": \" "+storys.get(0).getName()+"\", \"description\": \""+storys.get(0).getDescription()+"\", \"priority\": \""+storys.get(0).getPriorityAsInt()+"\"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveStory", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existente Story - low authority");
            throw new AssertionError(e);
        }


        body = "{ \"ID\": \""+ ID +"\", \"name\": \" "+name+"\", \"description\": \""+desc+"\", \"priority\": \""+prio+"\"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveStory", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        }  catch (AssertionError e){
            printWriterAddFailure("Fail: HTTP PostRequest nicht erfolgreich - neue Story - low authority");
            throw new AssertionError(e);
        }

        header.set("sessionID", "");
        body = "{ \"ID\": \""+storys.get(0).getID()+"\", \"name\": \" "+storys.get(0).getName()+"\", \"description\": \""+storys.get(0).getDescription()+"\", \"priority\": \""+storys.get(0).getPriorityAsInt()+"\"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveStory", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null sessionID");
            throw new AssertionError(e);
        }

        List<DAOUserStory> userStories = DAOUserStoryService.getAll();
        int id = userStories.get(userStories.size()-1).getId();

        DAOUserStoryService.deleteById(id);
        printWriterAddPass();
    }

    /*  Test ID: HTTP.T6
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Anfordern des Reset-codes für einen User
     */
    @Test
    @Tag("old")
    void RequestResetCodeTest(){
        printWriterAddTest("RequestResetCode", "T6");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String EMail = "Fail@Mail.com";

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/RequestResetCode", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/RequestResetCode?email=" + EMail, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neuer User");
            throw new AssertionError(e);
        }

        ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + port + "/RequestResetCode?email=" + accounts.get(0).getMail(), HttpMethod.POST, message, String.class);

        try {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existenter User");
            throw new AssertionError(e);
        }
        
        try {
            try {
                assertThat(Objects.requireNonNull(response.getBody()).contains("code:"));
            } catch (AssertionError e) {
                printWriterAddFailure("ResetCode nicht integriert");
                throw new AssertionError(e);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        response = this.restTemplate.exchange("http://localhost:" + port + "/RequestResetCode?email=" + "", HttpMethod.POST, message, String.class);

        try {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null User");
            throw new AssertionError(e);
        }

        try {
            assertTrue(Objects.requireNonNull(response.getBody()).contains("<title>Error</title>"));
        } catch (AssertionError e){
            printWriterAddFailure("Wrong Redirect - null User");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T7
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum resetten des Passworts eines Users
     */
    @Test
    @Tag("old")
    void RequestResetPasswortTest(){
        printWriterAddTest("RequestResetPasswort", "T7");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String EMail = "Test@FailMail.com";
        String Passwort = "TestPasswort123";
        
        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/RequestResetPasswort", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/RequestResetPasswort?email=" + accounts.get(0).getMail() + "&newPasswort="+ accounts.get(0).getPassword() + "&code=" + "Baum", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existenter User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/RequestResetPasswort?email=" + EMail + "&newPasswort="+ Passwort + "&code=" + "Baum", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neuer User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/RequestResetPasswort?email=" + "" + "&newPasswort="+ Passwort + "&code=" + "Baum", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich -null User");
            throw new AssertionError(e);
        }

        ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + port + "/RequestResetPasswort?email=" + accounts.get(0).getMail() + "&newPasswort="+ "" + "&code=" + "Baum", HttpMethod.POST, message, String.class);

        try {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich -null PW");
            throw new AssertionError(e);
        }

        try {
            assertTrue(Objects.requireNonNull(response.getBody()).contains("<title>Error</title>"));
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null PW");
            throw new AssertionError(e);
        }
        
        printWriterAddPass();
    }

    /*  Test ID: HTTP.T8
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Senden einer Mail an einen User
     */
    @Test
    @Tag("old")
    void SendMail(){
        printWriterAddTest("SendMail", "T8");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String EMail = "Test@Mail.com";
        String Text = "Test Mail"+formatter.format(date);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/SendMail", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", masterID);
        message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/SendMail" +"?senderEmail=" + EMail + "&recieverEmail="+ EMail + "&text="+Text, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neuer User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/SendMail?senderEmail=" + accounts.get(0).getMail() + "&recieverEmail="+ accounts.get(1).getMail() + "&text="+Text, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Fail: HTTP PostRequest nicht erfolgreich");
            throw new AssertionError(e);
        }

        header.set("sessionID", "");
        message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/SendMail" +"?senderEmail=" + EMail + "&recieverEmail="+ EMail + "&text="+Text, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null session");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/SendMail?senderEmail=" + accounts.get(0).getMail() + "&recieverEmail="+ accounts.get(1).getMail() + "&text="+Text, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Fail: HTTP PostRequest nicht erfolgreich - null Session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T9
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Öffnen einer ProfilPage by UserID/sessionID
     */
    @Test
    @Tag("old")
    void getProfilPageByID(){
        printWriterAddTest("getProfilPageByID", "T9");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getProfilePage", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getProfilePage?sessionID=" + sessions.get(0), HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existenter User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getProfilePage?sessionID=" + masterID, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neuer User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getProfilePage?sessionID=" + "", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null Session");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getProfilePage?sessionID=" + "99999999", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - invalid Session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T10
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Öffnen einer ProfilPage by Email
     */
    @Test
    @Tag("old")
    void getProfilePageByEmail(){
        printWriterAddTest("getProfilPageByEmail", "T10");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String Email = "Test@FailMail.com";

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getProfilePageByEmail", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getProfilePageByEmail?sessionID=" + masterID +"&email=" + Email, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neuer User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getProfilePageByEmail?sessionID=" + sessions.get(1) +"&email=" + accounts.get(0).getMail(), HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existenter User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getProfilePageByEmail?sessionID=" + sessions.get(1) +"&email=" + "", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null User");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getProfilePageByEmail?sessionID=" + "" +"&email=" + accounts.get(0).getMail(), HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null Session");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getProfilePageByEmail?sessionID=" + "9999" +"&email=" + accounts.get(0).getMail(), HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - invalid Session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T11
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Speichern neuer Userdaten im Profil
     */
    @Test
    @Tag("old")
    void saveUserData(){
        printWriterAddTest("saveUserData", "T11");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        HttpEntity<String> message = new HttpEntity<>(body, header);
        int ID = 0;
        String uname = "newDave";
        String email = "newDave@FailMail.com";
        String role = null;
        String pdesc = "Ein Noob";
        String wdesc = "Noobleader";

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveUserData", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", sessions.get(0));
        body = "{\"uname\": \""+ profils.get(0).getUname()+"\", \"email\": \""+ accounts.get(0).getMail() +"\", \"privatDesc\": \""+ profils.get(0).getPrivatDesc() +"\", \"workDesc\": \""+ profils.get(0).getWorkDesc() +"\", \"roles\": "+ profils.get(0).getRoles() +"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveUserData", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existenter User");
            throw new AssertionError(e);
        }

        header.set("sessionID", masterID);
        body = "{\"uname\": \""+ uname +"\", \"email\": \""+ email +"\", \"privatDesc\": \""+ pdesc +"\", \"workDesc\": \""+ wdesc +"\", \"roles\": "+ role +"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveUserData", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neuer User");
            throw new AssertionError(e);
        }

        header.set("sessionID", "9999999");
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveUserData", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - invalid Session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T12
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Ausgeben aller Task
     */
    @Test
    @Tag("old")
    void getAllTask(){
        printWriterAddTest("getAllTask", "T12");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        int ID = 0;
        ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + port + "/getAllTask?sessionID=" + sessions.get(0), HttpMethod.GET, message, String.class);

        try {
            assertThat(response.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Request fehlgeschlagen");
            throw new AssertionError(e);
        }

        try {
            assertTrue(Objects.requireNonNull(response.getBody()).contains("<title>Projektmanager-Tasks</title>"));
        } catch (AssertionError e){
            printWriterAddFailure("Page nicht geladen - existente Session");
            throw new AssertionError(e);
        }

        try {
            assertTrue(this.restTemplate.exchange("http://localhost:"+ port + "/getAllTask?sessionID=" + ID, HttpMethod.GET, message, String.class).getBody().contains("<title>Login</title>"));
        } catch (AssertionError e){
            printWriterAddFailure("Accepted invalid session");
            throw new AssertionError(e);
        }

        try {
            assertTrue(this.restTemplate.exchange("http://localhost:"+ port + "/getAllTask?sessionID=" + "", HttpMethod.GET, message, String.class).getBody().contains("<title>Error</title>"));
        } catch (AssertionError e){
            printWriterAddFailure("Accepted null session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T13
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Ausgeben aller Task by USID
     */
    @Test
    @Tag("old")
    void getTaskByUSID(){
        printWriterAddTest("getTaskByUSID", "T13");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        int ID = 0;

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskByUSID", HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskByUSID?USID="+ storys.get(0).getID() + "&sessionID=" + masterID, HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existente Story");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskByUSID?USID="+ ID + "&sessionID=" + masterID, HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neue Story");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskByUSID?USID="+ storys.get(0).getID() + "&sessionID=" + "", HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existente Story - null Session");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskByUSID?USID="+ storys.get(0).getID() + "&sessionID=" + "999999", HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existente Story - null Session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T14
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Ausgeben aller Task by TLID
     */
    @Test
    @Tag("old")
    void getTaskByTLID(){
        printWriterAddTest("getTaskByTLID", "T14");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        int ID = 0;

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskByTLID", HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskByTLID?TLID="+ boards.get(0).getTaskListList().get(0).getID() + "&sessionID=" + masterID, HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskByTLID?TLID="+ID + "&sessionID=" + masterID, HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neue TaskList");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskByTLID?TLID="+ boards.get(0).getTaskListList().get(0).getID() + "&sessionID=" + "", HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null Session");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskByTLID?TLID="+ boards.get(0).getTaskListList().get(0).getID() + "&sessionID=" + "999999", HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - invalid Session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T15
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Speichern neuer und überspeichern existenter Task
     */
    @Test
    @Tag("old")
    void saveTask() {
        printWriterAddTest("saveTask", "T15");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveTask", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e) {
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", masterID);
        String body = "{\"description\":\"New Task To Describe\",\"userStoryID\":1,\"timeNeededG\":-1,\"timeNeededA\":-1,\"dueDate\":\"2024-10-05T12:39\",\"priority\":1,\"tID\":-1,\"tbID\":-1}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveTask", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e) {
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neue Task");
            throw new AssertionError(e);
        }

        header.set("sessionID", masterID);
        body = "{\"description\":\""+ tasks.get(0).getDescription() +"\",\"userStoryID\":"+ tasks.get(0).getUserStoryID() +",\"timeNeededG\":"+ tasks.get(0).getTimeNeededG() +",\"timeNeededA\":"+ tasks.get(0).getTimeNeededA() +",\"dueDate\":\""+ tasks.get(0).getDueDateAsString() +"\",\"priority\":"+ tasks.get(0).getPriorityAsInt() +",\"tID\":"+ tasks.get(0).getID() + ",\"tbID\":"+ tasks.get(0).getTbID() + "}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveTask", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e) {
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existente Task");
            throw new AssertionError(e);
        }

        header.set("sessionID", "99999");
        body = "{\"description\":\""+ tasks.get(0).getDescription() +"\",\"userStoryID\":"+ tasks.get(0).getUserStoryID() +",\"timeNeededG\":"+ tasks.get(0).getTimeNeededG() +",\"timeNeededA\":"+ tasks.get(0).getTimeNeededA() +",\"dueDate\":\""+ tasks.get(0).getDueDateAsString() +"\",\"priority\":"+ tasks.get(0).getPriorityAsInt() +",\"tID\":"+ tasks.get(0).getID() + ",\"tbID\":"+ tasks.get(0).getTbID() + "}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveTask", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.FORBIDDEN);
        } catch (AssertionError e) {
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existente Task - invalid Session");
            throw new AssertionError(e);
        }

        header.set("sessionID", "");
        body = "{\"description\":\""+ tasks.get(0).getDescription() +"\",\"userStoryID\":"+ tasks.get(0).getUserStoryID() +",\"timeNeededG\":"+ tasks.get(0).getTimeNeededG() +",\"timeNeededA\":"+ tasks.get(0).getTimeNeededA() +",\"dueDate\":\""+ tasks.get(0).getDueDateAsString() +"\",\"priority\":"+ tasks.get(0).getPriorityAsInt() +",\"tID\":"+ tasks.get(0).getID() + ",\"tbID\":"+ tasks.get(0).getTbID() + "}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveTask", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e) {
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existente Task - null Session");
            throw new AssertionError(e);
        }

        List<DAOTask> dt = DAOTaskService.getAll();
        DAOTaskService.deleteById(dt.get(dt.size()-1).getId());

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T16
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Löschen einer Task by ID
     */
    @Test
    @Tag("old")
    void DeleteTaskByID(){
        printWriterAddTest("DeleteTaskByID", "T16");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        int TID = 1;

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/deleteTask", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", masterID);
        message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/deleteTask?tID="+TID, HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neue Task");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/deleteTask?tID="+tasks.get(2).getID(), HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existente Task");
            throw new AssertionError(e);
        }

        header.set("sessionID", "999999999");
        message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/deleteTask?tID="+tasks.get(2).getID(), HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.FORBIDDEN);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existente Task - invalid Session");
            throw new AssertionError(e);
        }

        header.set("sessionID", "");
        message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/deleteTask?tID="+tasks.get(2).getID(), HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existente Task - null Session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T17
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Anzeigen aller User
     */
    @Test
    @Tag("old")
    void getUserList(){
        printWriterAddTest("getUserList","T17");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getUserList", HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", masterID);
        message = new HttpEntity<>("", header);

        ResponseEntity<String> responce = this.restTemplate.exchange("http://localhost:" + port + "/getUserList?sessionID=" + sessions.get(0), HttpMethod.GET, message, String.class);
        try {
            assertThat(responce.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP GetRequest nicht erfolgreich");
            throw new AssertionError(e);
        }

        try {
            assertTrue(Objects.requireNonNull(responce.getBody()).contains("<title>Projektmanager-Nutzer</title>"));
        } catch (AssertionError e){
            printWriterAddFailure("Wrong Page loaded - Projektmanager-Nutzer");
            throw new AssertionError(e);
        }

        responce = this.restTemplate.exchange("http://localhost:" + port + "/getUserList?sessionID=" + "", HttpMethod.GET, message, String.class);
        try {
            assertThat(responce.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP GetRequest nicht erfolgreich - null Session");
            throw new AssertionError(e);
        }

        try {
            assertTrue(Objects.requireNonNull(responce.getBody()).contains("<title>Error</title>"));
        } catch (AssertionError e){
            printWriterAddFailure("Wrong Page loaded - Error");
            throw new AssertionError(e);
        }

        responce = this.restTemplate.exchange("http://localhost:" + port + "/getUserList?sessionID=" + "999999", HttpMethod.GET, message, String.class);
        try {
            assertThat(responce.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP GetRequest nicht erfolgreich - invalid Session");
            throw new AssertionError(e);
        }

        try {
            assertTrue(Objects.requireNonNull(responce.getBody()).contains("<title>Login</title>"));
        } catch (AssertionError e){
            printWriterAddFailure("Wrong Page loaded - Login");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T18
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Anzeigen eines TaskBoard by ID
     */
    @Test
    @Tag("old")
    void getTaskBoardByID(){
        printWriterAddTest("getTaskBoardByID", "T18");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskBoardByID", HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskBoardByID?sessionID=" + masterID+"&TBID="+-1, HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neues Taskboard");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskBoardByID?sessionID=" + "99999999" +"&TBID="+ boards.get(0).getID(), HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existentes Taskboard - invalid Session");
            throw new AssertionError(e);
        }

        ResponseEntity<String> responce = this.restTemplate.exchange("http://localhost:" + port + "/getTaskBoardByID?sessionID=" + sessions.get(0)  + "&TBID=" + boards.get(0).getID(), HttpMethod.GET, message, String.class);
        try {
            assertThat(responce.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP GetRequest nicht erfolgreich - existentes Taskboard");
            throw new AssertionError(e);
        }

        try {
            assertTrue(Objects.requireNonNull(responce.getBody()).contains(boards.get(0).getName()));
        } catch (AssertionError e){
            printWriterAddFailure("Wrong Page loaded");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T19
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum speicher/erstellen eines TaskBoard
     */
    @Test
    @Tag("old")
    void saveTaskBoardTest(){
        printWriterAddTest("saveTaskBoard", "T19");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        String body = "";
        HttpEntity<String> message = new HttpEntity<>(body, header);
        int TBID = -1;
        String TBName = "To Delete";

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveTaskBoard", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e) {
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", masterID);
        body = "{\"tbID\": \""+TBID+"\", \"name\": \""+TBName+"\", \"taskListList\": "+null+"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveTaskBoard" , HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e) {
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neue TaskBoard");
            throw new AssertionError(e);
        }

        body = "{\"tbID\": \""+boards.get(0).getID()+"\", \"name\": \""+boards.get(0).getName()+"\", \"taskListList\": "+null+"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveTaskBoard", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e) {
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existentes TaskBoard");
            throw new AssertionError(e);
        }

        header.set("sessionID", "99999999");
        body = "{\"tbID\": \""+boards.get(0).getID()+"\", \"name\": \""+boards.get(0).getName()+"\", \"taskListList\": "+null+"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveTaskBoard", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.FORBIDDEN);
        } catch (AssertionError e) {
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existentes TaskBoard - invalid Session");
            throw new AssertionError(e);
        }

        header.set("sessionID", "");
        body = "{\"tbID\": \""+boards.get(0).getID()+"\", \"name\": \""+boards.get(0).getName()+"\", \"taskListList\": "+null+"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveTaskBoard", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e) {
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existentes TaskBoard - null Session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T20
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Anzeigen eines TaskBoard
     */
    @Test
    @Tag("old")
    void getTaskBoard(){
        printWriterAddTest("getTaskBoard", "T20");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskBoard", HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskBoard?sessionID=" + "99999999", HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - invalid Session");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskBoard?sessionID=" + "", HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - null Session");
            throw new AssertionError(e);
        }

        ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + port + "/getTaskBoard?sessionID=" + sessions.get(0), HttpMethod.GET, message, String.class);

        try {
            assertThat(response.getStatusCode().isSameCodeAs(HttpStatus.OK));
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich");
            throw new AssertionError(e);
        }

        try {
            assertTrue(Objects.requireNonNull(response.getBody()).contains("<title>Task Board</title>"));
        } catch (AssertionError e){
            printWriterAddFailure("Wrong Page loaded");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T21
     *  Author: Lucas Krüger
     *  Zweck: Testen des Get Requests zum Updaten der Authorität eines Nutzers
     */
    @Test
    @Tag("old")
    void setAuthorityTest(){
        printWriterAddTest("setAuthority", "T21");
        String url = "/setAuthority";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url, HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try{
            presentationToLogic.accountService.setAuthority(users.get(0).getID(), 1);
        } catch (Exception e){
            e.printStackTrace();
        }

        header.set("sessionID", sessions.get(0));
        message = new HttpEntity<>("", header);
        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?USID="+users.get(0).getID() + "&Auth=" +3, HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.FORBIDDEN);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request von low Auth");
            throw new AssertionError(e);
        }

        header.set("sessionID", sessions.get(2));
        message = new HttpEntity<>("", header);
        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?USID="+users.get(0).getID() + "&Auth=" +3, HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - Valid Params");
            throw new AssertionError(e);
        }

        header.set("sessionID", "999999");
        message = new HttpEntity<>("", header);
        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?USID="+users.get(0).getID() + "&Auth=" +3, HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.FORBIDDEN);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - Valid Params - invalid Session");
            throw new AssertionError(e);
        }

        header.set("sessionID", "");
        message = new HttpEntity<>("", header);
        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?USID="+users.get(0).getID() + "&Auth=" +3, HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - Valid Params - null Session");
            throw new AssertionError(e);
        }

        try{
            presentationToLogic.accountService.setAuthority(users.get(0).getID(), 1);
        } catch (Exception e){
            e.printStackTrace();
        }
        printWriterAddPass();
    }

    @Test
    @Tag("old")
    /*  Test ID: HTTP.T22
     *  Author: Lucas Krüger
     *  Zweck: Testen des Get Requests zum Updaten der Authorität eines Nutzers
     */
    void setTaskListOfTask(){
        printWriterAddTest("setTaskListOfTask", "T22");
        String url = "/setTaskListToTask";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url, HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try{
            presentationToLogic.taskService.setTaskList(tasks.get(0).getID(), boards.get(0).getTaskListList().get(0).getID());
        } catch (Exception e){
            e.printStackTrace();
        }

        header.set("sessionID", sessions.get(0));
        message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TID="+ tasks.get(0).getID() + "&TLID=" + 9999999, HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Invalid TLID");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TID="+ 9999999 + "&TLID=" + 9999999, HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Invalid TID");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TID="+ tasks.get(0).getID() + "&TLID=" + boards.get(0).getTaskListList().get(1).getID(), HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - Valid Params - Valid TaskList");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TID="+ tasks.get(0).getID() + "&TLID=" + "", HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT); // Weil Funktion existiert nicht
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - Valid Params - null TaskList");
            throw new AssertionError(e);
        }

        header.set("sessionID", "");
        message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TID="+ tasks.get(0).getID() + "&TLID=" + boards.get(0).getTaskListList().get(1).getID(), HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - Valid Params - Valid TaskList - null Session");
            throw new AssertionError(e);
        }

        header.set("sessionID", "99999");
        message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TID="+ tasks.get(0).getID() + "&TLID=" + boards.get(0).getTaskListList().get(1).getID(), HttpMethod.GET, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.FORBIDDEN);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - Valid Params - Valid TaskList - invalid Session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T23
     *  Author: Lucas Krüger
     *  Zweck: löschen eines TaskBoards
     */
    @Test
    @Tag("new")
        void deleteTaskBoard(){
        printWriterAddTest("deleteTaskBoard", "T23");
        String url = "/deleteTaskBoard";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN); // MediaType.APPLICATION_JSON
        HttpEntity<String> message = new HttpEntity<>("", header);
        HttpMethod method = HttpMethod.POST; // HttpMethod.Post

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url, method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", sessions.get(0));
        message = new HttpEntity<>("", header); // Mit SessionID

        try{
            presentationToLogic.taskBoardService.createTaskBoard("toDelete");
        } catch (Exception e){
            e.printStackTrace();
        }

        List<DAOTaskBoard> dtbs = DAOTaskBoardService.getAll();
        DAOTaskBoard dtb = dtbs.get(dtbs.size()-1);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TBID=" + dtb.getId(), method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - existentes TaskBoard");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TBID=" + dtb.getId(), method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - nicht existentes TaskBoard");
            throw new AssertionError(e);
        }

        header.set("sessionID", "");
        message = new HttpEntity<>("", header); // Mit SessionID

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TBID=" + dtb.getId(), method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - existentes TaskBoard - null Session");
            throw new AssertionError(e);
        }

        header.set("sessionID", "999999999");
        message = new HttpEntity<>("", header); // Mit SessionID

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TBID=" + dtb.getId(), method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.FORBIDDEN);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - existentes TaskBoard - invalid Session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T24
     *  Author: Lucas Krüger
     *  Zweck: Testet alle Funktionen ohne wirkliche Logic
     */
    @Test
    @Tag("new")
        void FunktionsToRedirect(){
        printWriterAddTest("FunktionsToRedirect", "T24");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN); // MediaType.APPLICATION_JSON
        HttpEntity<String> message = new HttpEntity<>("", header);
        HttpMethod method = HttpMethod.GET; // HttpMethod.Post

        String url = "/PasswortForgotPage";
        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url, method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Request Fehlgeschlagen");
            throw new AssertionError(e);
        }

        url = "/RegisterPage";
        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url, method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Request Fehlgeschlagen");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T25
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Ausgeben aller Task
     */
    @Test
    @Tag("new")
    void ProjectManager(){
        printWriterAddTest("ProjectManager", "T25");
        String url = "/ProjectManager";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN); // MediaType.APPLICATION_JSON
        HttpEntity<String> message = new HttpEntity<>("", header);
        HttpMethod method = HttpMethod.GET; // HttpMethod.Post

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url, method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", sessions.get(0));
        message = new HttpEntity<>("", header); // Mit SessionID

        ResponseEntity<String> response = this.restTemplate.exchange("http://localhost:" + port + url + "?sessionID=" + sessions.get(0), method, message, String.class);

        try {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - valid session");
            throw new AssertionError(e);
        }

        try {
            assertTrue(Objects.requireNonNull(response.getBody()).contains("<title>Projektmanager</title>"));
        } catch (AssertionError e){
            printWriterAddFailure("Wrong redirect - valid session");
            throw new AssertionError(e);
        }

        response = this.restTemplate.exchange("http://localhost:" + port + url + "?sessionID=" + "", method, message, String.class);

        try {
            assertThat(response.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - null session");
            throw new AssertionError(e);
        }

        try {
            assertTrue(Objects.requireNonNull(response.getBody()).contains("<title>Error</title>"));
        } catch (AssertionError e){
            printWriterAddFailure("Wrong redirect - null session");
            throw new AssertionError(e);
        }

        response = this.restTemplate.exchange("http://localhost:" + port + url + "?sessionID=" + "9999", method, message, String.class);

        try {
            assertThat(response.getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - invalid session");
            throw new AssertionError(e);
        }

        try {
            assertTrue(Objects.requireNonNull(response.getBody()).contains("<title>Login</title>"));
        } catch (AssertionError e){
            printWriterAddFailure("Wrong redirect - valid session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T26
     *  Author: Lucas Krüger
     *  Zweck: Testet Controller Funktion setRealTaskTime
     */
    @Test
    @Tag("new")
        void setRealTaskTime(){
        printWriterAddTest("setRealTaskTime", "T26");
        String url = "/setRealTaskTime";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN); // MediaType.APPLICATION_JSON
        HttpEntity<String> message = new HttpEntity<>("", header);
        HttpMethod method = HttpMethod.POST; // HttpMethod.Post

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url, method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", sessions.get(0));
        message = new HttpEntity<>("", header); // Mit SessionID

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TID=" + tasks.get(0).getID() + "&time=" + tasks.get(0).getTimeNeededA(), method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - valid Session");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TID=" + "" + "&time=" + tasks.get(0).getTimeNeededA(), method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - null tID");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TID=" + tasks.get(0).getTbID() + "&time=" + -1, method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - neg time");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TID=" + 9999 + "&time=" + tasks.get(0).getTimeNeededA(), method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - invalid tID");
            throw new AssertionError(e);
        }

        header.set("sessionID", "9999999");
        message = new HttpEntity<>("", header); // Mit SessionID

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TID=" + tasks.get(0).getTbID() + "&time=" + + tasks.get(0).getTimeNeededA(), method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.FORBIDDEN);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - invalid Session");
            throw new AssertionError(e);
        }

        header.set("sessionID", "");
        message = new HttpEntity<>("", header); // Mit SessionID

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?TID=" + tasks.get(0).getTbID() + "&time=" + + tasks.get(0).getTimeNeededA(), method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - null Session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T27
     *  Author: Lucas Krüger
     *  Zweck:
     */
    @Test
    @Tag ("new")
    void deleteUS() throws Exception {
        printWriterAddTest("deleteUS", "T27");
        String url = "/deleteUS";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN); // MediaType.APPLICATION_JSON
        HttpEntity<String> message = new HttpEntity<>("", header);
        HttpMethod method = HttpMethod.POST; // HttpMethod.Post

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url, method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", sessions.get(1));
        message = new HttpEntity<>("", header); // Mit SessionID

        presentationToLogic.userStoryService.saveUserStory(new UserStory("toDelete","toDelete", 1, -1));
        List<DAOUserStory> dusl = DAOUserStoryService.getAll();
        int ID = dusl.get(dusl.size()-1).getId();

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?ID=" + ID, method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.FORBIDDEN);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - valid Session - low Auth");
            throw new AssertionError(e);
        }

        header.set("sessionID", sessions.get(2));
        message = new HttpEntity<>("", header); // Mit SessionID

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?ID=" + ID, method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - valid Session");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?ID=" + "", method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - null USID");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?ID=" + 9999, method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - invalid USID");
            throw new AssertionError(e);
        }

        header.set("sessionID", "9999999");
        message = new HttpEntity<>("", header); // Mit SessionID

        presentationToLogic.userStoryService.saveUserStory(new UserStory("toDelete","toDelete", 1, -1));
        dusl = DAOUserStoryService.getAll();
        ID = dusl.get(dusl.size()-1).getId();

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?ID=" + ID , method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.FORBIDDEN);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - invalid Session");
            throw new AssertionError(e);
        }

        header.set("sessionID", "");
        message = new HttpEntity<>("", header); // Mit SessionID

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?ID=" + tasks.get(0).getTbID() , method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.CONFLICT);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - null Session");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

}


    /*  Test ID: HTTP.T
         *  Author: Lucas Krüger
         *  Zweck:
         *//*
    @Test
    @Tag("new")
        void (){
        printWriterAddTest("", "T");
        String url = "/";
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN); // MediaType.APPLICATION_JSON
        HttpEntity<String> message = new HttpEntity<>("", header);
        HttpMethod method = HttpMethod.GET; // HttpMethod.Post

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url, method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", sessions.get(0));
        String body = "{\"tbID\": \""+TBID+"\", \"name\": \""+TBName+"\"}";
        message = new HttpEntity<>("", header); // Mit SessionID

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + url + "?", method, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Request nicht - ");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }
     */
