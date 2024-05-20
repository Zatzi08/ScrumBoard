package com.team3.project.Tests;

import com.team3.project.Classes.Email;
import com.team3.project.DAO.DAOTask;
import com.team3.project.DAO.DAOUserStory;
import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.DAOService.DAOTaskService;
import com.team3.project.DAOService.DAOUserStoryService;
import com.team3.project.Tests.BaseClassesForTests.BaseHTTPTest;
import com.team3.project.service.AccountService;
import org.junit.jupiter.api.*;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.yaml.snakeyaml.events.Event;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Suite
public class HttpRequestTests extends BaseHTTPTest {
    @LocalServerPort
    private int port;
    private final String masterID = "EAIFPH8746531";
    @Autowired
    public TestRestTemplate restTemplate;
    @Autowired
    private AccountService accountService;
    @Autowired
    private Email email;

    @BeforeAll
    public static void BeforeAll(){
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
        after();
    }

    @AfterAll
    public static void afterAll() {
        tearDown();
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
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login" + "?EMail=" + accounts.get(0).getMail() + "&Passwort=" + accounts.get(0).getMail(), HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login" + "?EMail=" + EMail + "&Passwort=" + Passwort, HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich");
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

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register" + "?EMail=" + accounts.get(0).getMail() + "&Passwort=" + accounts.get(0).getPassword() + "&Username=" + users.get(0).getName(), HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        }  catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existenter User");
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
    @Tag("revisited")
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
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existente Story");
            throw new AssertionError(e);
        }


        body = "{ \"ID\": \""+ ID +"\", \"name\": \" "+name+"\", \"description\": \""+desc+"\", \"priority\": \""+prio+"\"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveStory", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        }  catch (AssertionError e){
            printWriterAddFailure("Fail: HTTP PostRequest nicht erfolgreich - neue Story");
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

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T11
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Speichern neuer Userdaten im Profil
     */
    @Test
    @Tag("revisited")
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

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T14
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Ausgeben aller Task by TLID
     */
    @Test
    @Tag("new")
    void getTaskByTLID(){
        printWriterAddTest("getTaskByTLID", "T14");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        int ID = 0;

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskByTLID", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", masterID);
        message = new HttpEntity<>("", header);

        try { // TODO: Haben TL 1
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskByTLID?TLID="+ 1, HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskByTLID?TLID="+ID, HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neue TaskList");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T15
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Speichern neuer und überspeichern existenter Task
     */
    @Test
    @Tag("revisited")
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
        String body = "{\"description\":\"New Task To Describe\",\"userStoryID\":1,\"timeNeededG\":-1,\"timeNeededA\":-1,\"dueDate\":\"2024-10-05T12:39\",\"priority\":1,\"tID\":-1}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveTask", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e) {
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neue Task");
            throw new AssertionError(e);
        }

        header.set("sessionID", masterID);
        body = "{\"description\":\""+ tasks.get(0).getDescription() +"\",\"userStoryID\":"+ tasks.get(0).getUserStoryID() +",\"timeNeededG\":"+ tasks.get(0).getTimeNeededG() +",\"timeNeededA\":"+ tasks.get(0).getTimeNeededA() +",\"dueDate\":\""+ tasks.get(0).getDueDateAsString() +"\",\"priority\":"+ tasks.get(0).getPriorityAsInt() +",\"tID\":"+ tasks.get(0).getID() +"}";
        message = new HttpEntity<>(body, header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/saveTask", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e) {
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existente Task");
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

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T17
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Anzeigen eines Task Board by ID
     */
    @Test
    @Tag("new")
    void getTaskBoardByID(){
        printWriterAddTest("getTaskBoard","T17");
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        int TBID = 0;

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskBoard", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            printWriterAddFailure("Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        header.set("sessionID", masterID);
        message = new HttpEntity<>("", header);

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskBoard?sessionID=" + masterID+"&TBID="+TBID, HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - neues Taskboard");
            throw new AssertionError(e);
        }

        try { // TODO: existentes Taskboard
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/getTaskBoard?sessionID=" + masterID+"&TBID="+TBID, HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            printWriterAddFailure("HTTP PostRequest nicht erfolgreich - existentes Taskboard");
            throw new AssertionError(e);
        }

        printWriterAddPass();
    }

    /*  Test ID: HTTP.T17
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Anzeigen aller User
     */
    @Test
    @Tag("new")
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
            assertTrue(Objects.requireNonNull(responce.getBody()).contains("projectManager-Nutzer"));
        } catch (AssertionError e){
            printWriterAddFailure("Wrong Page loaded");
        }

        printWriterAddPass();
    }
}
