package com.team3.project.Tests;

import com.team3.project.DAO.DAOUserStory;
import com.team3.project.DAOService.DAOAccountService;
import com.team3.project.DAOService.DAOUserStoryService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTests {
    @LocalServerPort
    private int port;
    @Autowired
    public TestRestTemplate restTemplate;

    private static PrintWriter pw;
    private static Date date;
    private static SimpleDateFormat formatter;
    private boolean pass = true;

    @BeforeAll
    public static void setup(){
        try {
            File log = new File("src/test/java/com/team3/project/logs/log_LogicTest.txt");
            log.setWritable(true);
            log.setReadable(true);
            FileWriter fw = new FileWriter(log,true);
            pw = new PrintWriter(fw,true);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

        formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        date = new Date();
    }

    @BeforeEach
    public void before(){
        pw.append("\n\n");
        pass = true;
    }

    @AfterAll
    public static void closeWriter(){
        pw.close();
    }


    /* Test ID: HTTP.T1
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Login
     */
    @Test
    void LoginTest(){
        pw.append("HTTP-Test-LoginTest\nTest ID: HTTP.T1\nDate: " + formatter.format(date)+ '\n');
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String EMail1 = "Test@Mail.com";
        String Passwort1 = "TestPasswort123";
        String EMail2 = "Test@Mail.com";
        String Passwort2 = "TestFail123";
        try{
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login",HttpMethod.POST,message, String.class).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            pw.append("Fail: Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login" + "?EMail=" + EMail1 + "&Passwort=" + Passwort1, HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.FOUND);
        } catch (AssertionError e){
            pw.append("Fail: HTTP PostRequest nicht erfolgreich");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login" + "?EMail=" + EMail2 + "&Passwort=" + Passwort2, HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.FOUND);
        } catch (AssertionError e){
            pw.append("Fail: HTTP PostRequest nicht erfolgreich");
            throw new AssertionError(e);
        }
    }

    /*  Test ID: HTTP.T2
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Registrieren
     */
    @Test
    void RegisterTest(){
        pw.append("HTTP-Test-RegisterTest\nTest ID: HTTP.T2\nDate: " + formatter.format(date)+ '\n');
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String eMail1 = "Test@Mail.com";
        String eMail2 = "Test@FailMail.com";
        String passwort = "TestPasswort123";
        String username = "Test Testerson";

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        }  catch (AssertionError e){
            pw.append("Fail: Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register" + "?EMail=" + eMail1 + "&Passwort=" + passwort + "&Username=" + username, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        }  catch (AssertionError e){
            pw.append("Fail: HTTP PostRequest nicht erfolgreich");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register" + "?EMail=" + eMail2 + "&Passwort=" + passwort + "&Username=" + username, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            pw.append("Fail: HTTP PostRequest nicht erfolgreich");
            throw new AssertionError(e);
        }

        DAOAccountService.deleteAccount(eMail2);
    }

    /*  Test ID: HTTP.T3
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zur neuen Passwortpage
     */
    @Test
    void neuesPasswortPageTest(){
        pw.append("HTTP-Test-neuesPasswortPageTest\nTest ID: HTTP.T3\nDate: " + formatter.format(date)+ '\n');
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String eMail = "Test@Mail.com";
        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswortPage", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            pw.append("Fail: Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswortPage" + "?EMail=" + eMail, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            pw.append("Fail: HTTP PostRequest nicht erfolgreich");
            throw new AssertionError(e);
        }
    }

    /*  Test ID: HTTP.T4
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum neuen Passwort setzen
     */
    @Test
    void neuesPasswortTest(){
        pw.append("HTTP-Test-neuesPasswortTest\nTest ID: HTTP.T4\nDate: " + formatter.format(date)+ '\n');
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String eMail = "Test@Mail.com";
        String passwort = "TestPasswort123";
        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswort", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            pw.append("Fail: Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswort" + "?EMail=" + eMail + "&Passwort=" + passwort, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.OK);
        } catch (AssertionError e){
            pw.append("Fail: HTTP PostRequest nicht erfolgreich");
            throw new AssertionError(e);
        }
    }

    /*  Test ID: HTTP.T5
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum adden von neuen oder ändern bestehender USerStorys
     */
    @Test
    void addUserStory(){
        pw.append("HTTP-Test-addUserStoryTest\nTest ID: HTTP.T5\nDate: " + formatter.format(date)+ '\n');
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String name = "TestStory";
        String desc = "Delete later";
        int prio1 = 2;
        String desc2 = "Really delete later!";
        int prio2 = 1;

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/addStory", HttpMethod.POST, message, String.class).getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
        } catch (AssertionError e){
            pw.append("Fail: Akzeptiert Bad_Request");
            throw new AssertionError(e);
        }

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/addStory" + "?name=" + name + "&description=" + desc + "&priority=" + prio1 + "&id=-1", HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.FOUND);
        } catch (AssertionError e){
            pw.append("Fail: HTTP PostRequest nicht erfolgreich");
            throw new AssertionError(e);
        }

        List<DAOUserStory> userStories = DAOUserStoryService.getAll();
        int id = userStories.get(userStories.size()-1).getId();

        try {
            assertThat(this.restTemplate.exchange("http://localhost:" + port + "/addStory" + "?name=" + name + "&description=" + desc2 + "&priority=" + prio2 + "&id=" + id, HttpMethod.POST, message, String.class)
                    .getStatusCode()).isEqualTo(HttpStatus.FOUND);
        }  catch (AssertionError e){
            pw.append("Fail: HTTP PostRequest nicht erfolgreich");
            throw new AssertionError(e);
        }
        DAOUserStoryService.delete(id);
    }
}
