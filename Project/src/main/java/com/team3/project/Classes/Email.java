package com.team3.project.Classes;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;
@Getter
@Setter
@Component

/*
* aws Acc:
* E-Mail: henryfreyschmidt@gmail.com
* account name: SWT-Projekt
* passwort: SWTGruppe3Team3!
* kontaktinfo: Henry's persönliche Daten
* Problem: meine Karte wird nicht angenommen. Kläre ich am Montag (15.4) mit der Bank */
public class Email {

    public JavaMailSenderImpl emailSender = getJavaMailSender();

    @Bean
    public JavaMailSenderImpl getJavaMailSender() {
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

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        Email email = new Email();
        message.setFrom("gruppe3team3@gmail.com"); //
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        email.emailSender.send(message);
    }

    public void sendEmail(String Email){ //Authentifizierungscode an Email aus Argument schicken
        int test = 10;
        this.sendSimpleMessage(Email, "Test", String.format("Ihr Authentifikationscode ist wie folgt: %d",test));
    }
}
