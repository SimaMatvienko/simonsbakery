package com.simonsbakery.core.data;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by sima on 14.01.2017.
 */
@Controller
public class ContactController {

    @PostMapping("/contact")
    public String contact(@RequestParam(name = "name") String name,
                          @RequestParam(name = "email") String email,
                          @RequestParam(name = "message") String messageStr,
                          @RequestParam(name = "subject") String subject,
                          Model model
                          ) throws AddressException {


        final String username = "simonsbakerykiev@gmail.com";
        final String password = "simonsbakery1";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("simalovegod@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("New order came from"+name+", email:"+email+"subject: "+subject+"message: "+messageStr);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


        System.out.println(name);
        return "confiirm";
    }
}
