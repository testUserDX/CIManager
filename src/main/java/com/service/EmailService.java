package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@PropertySource(value = {"classpath:/properties/email.properties"})
public class EmailService {

    public static final String SUCCESS_COMMIT_MESSAGE = "Commit has succeeded";
    public static final String FAILED_COMMIT_MESSAGE = "Commit has not succeeded";

    @Autowired
    private Environment env;

    //to send multiple emails they should be separated by comma for example "email1@gmail.com,email2@gmail.com,email3@gmail.com"
    public Boolean sendEmail(String username, String subject, String text) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(env.getProperty("email.username"),"email.password");
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(env.getProperty("email.username")));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            return false;
        }
        return true;
    }
}
