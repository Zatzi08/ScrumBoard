package com.team3.project.Tests;

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
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTests {
    @LocalServerPort
    private int port;
    @Autowired
    public TestRestTemplate restTemplate;

    @Test
    void LoginTest(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String EMail = "Banane";
        String Passwort = "Banana";
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login",HttpMethod.POST,message, String.class).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Login" + "?EMail=" + EMail + "&Passwort=" + Passwort, HttpMethod.POST, message, String.class).getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    void RegisterTest(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String eMail = "Banane";
        String passwort = "Banana";
        String username = "Baba";
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register", HttpMethod.POST,message, String.class).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/Register" + "?EMail=" + eMail + "&Passwort=" + passwort + "&Username=" + username, HttpMethod.POST, message, String.class)
                .getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void neuesPasswortTest(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> message = new HttpEntity<>("", header);
        String eMail = "Banane";
        String passwort = "Banana";
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswort", HttpMethod.POST,message, String.class).getStatusCode())
                .isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(this.restTemplate.exchange("http://localhost:" + port + "/neuesPasswort" + "?EMail=" + eMail + "&Passwort=" + passwort, HttpMethod.POST, message, String.class)
                .getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
