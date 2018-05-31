package com.cyandrone.api.services;

import com.cyandrone.api.Configuration;
import com.cyandrone.api.models.WebhookMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.core.Response;
import java.util.Properties;

public class EmailService {

    public static Response sendEmail(WebhookMessage m){

        Properties properties = new Properties();
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port", 587);


        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(Configuration.from,Configuration.passFrom);
            }
        });

        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Configuration.from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(Configuration.to));
            message.setSubject("Test WebHook");

            //Invio messaggio del webhook per testarlo ed avere id ultimo messaggio mandato
            message.setContent(m.getData().getId(),"text/html");
            Transport.send(message);
            System.out.println("Messaggio inviato correttamente");


        } catch (MessagingException mex){
            mex.printStackTrace();

        }

        return Response.ok().build();
    }

}
