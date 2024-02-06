package com.team3.project.Classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;
@Getter
@Setter
@Component
//erst für Sprint 2
//TODO: Henry: checke ob gmail wieder freigegeben wurde
public class Email {
    @Autowired
    public JavaMailSenderImpl emailSender = getJavaMailSender();
    @Bean
    public JavaMailSenderImpl getJavaMailSender() { //Absender + relevante Daten für die Authentifizierungsmail festlegen
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        //
        mailSender.setUsername("gruppe3team3@gmail.com");
        mailSender.setPassword("bbxy ensm nmjf vdig");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }
    public void sendSimpleMessage(String to, String subject, String text) { //Details der Email festlegen
        SimpleMailMessage message = new SimpleMailMessage();
        Email email = new Email();
        message.setFrom("gruppe3team3@gmail.com"); //Absender
        message.setTo(to); //Empfänger, also User-Email
        message.setSubject(subject);//Überschrift
        message.setText(text); //Inhalt
        email.emailSender.send(message); //Email verschicken
    }
    public void sendEmail(String Email){ //Authentifizierungscode an Email aus Argument schicken
        //TODO: getAuthentification(Email): Zahl aus DB ablesen
        //getAuthentification mit beliebigen int simuliert
        int test = 10;
        this.sendSimpleMessage(Email, "Test", String.format("Ihr Authentifikationscode ist wie folgt: %d",test));

    }
}
