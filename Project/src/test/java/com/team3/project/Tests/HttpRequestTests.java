package com.team3.project.Tests;

import com.team3.project.DAOService.DAOAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTests {
    @LocalServerPort
    private int port;
    @Autowired
    public TestRestTemplate restTemplate;

    /* Test ID: HTTP.T1
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Login
     */
    @Test
    void LoginTest(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String EMail1 = "Test@Mail.com";
        String Passwort1 = "TestPasswort123";
        String EMail2 = "Test@Mail.com";
        String Passwort2 = "TestFail123";
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login",HttpMethod.POST,message, String.class).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login" + "?EMail=" + EMail1 + "&Passwort=" + Passwort1, HttpMethod.POST, message, String.class).getStatusCode())
                .isEqualTo(HttpStatus.OK);
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login" + "?EMail=" + EMail2 + "&Passwort=" + Passwort2, HttpMethod.POST, message, String.class).getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }

    /*  Test ID: HTTP.T2
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum Registrieren
     */
    @Test
    void RegisterTest(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String eMail1 = "Test@Mail.com";
        String eMail2 = "Test@FailMail.com";
        String passwort = "TestPasswort123";
        String username = "Test Testerson";
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register", HttpMethod.POST,message, String.class).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register" + "?EMail=" + eMail1 + "&Passwort=" + passwort + "&Username=" + username, HttpMethod.POST, message, String.class)
                .getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register" + "?EMail=" + eMail2 + "&Passwort=" + passwort + "&Username=" + username, HttpMethod.POST, message, String.class)
                .getStatusCode()).isEqualTo(HttpStatus.OK);
        DAOAccountService.deleteAccount(eMail2);
    }

    /*  Test ID: HTTP.T3
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zur neuen Passwortpage
     */
    @Test
    void neuesPasswortPageTest(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String eMail = "Test@Mail.com";
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswortPage", HttpMethod.POST,message, String.class).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswortPage" + "?EMail=" + eMail, HttpMethod.POST, message, String.class)
                .getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    /*  Test ID: HTTP.T4
     *  Author: Lucas Krüger
     *  Zweck: Testen des Post Requests zum neuen Passwort setzen
     */
    @Test
    void neuesPasswortTest(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String eMail = "Test@Mail.com";
        String passwort = "TestPasswort123";
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswort", HttpMethod.POST,message, String.class).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswort" + "?EMail=" + eMail + "&Passwort=" + passwort, HttpMethod.POST, message, String.class)
                .getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
