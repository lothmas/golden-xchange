package com.golden_xchange.domain.utilities;

import com.sun.mail.smtp.SMTPMessage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.util.Properties;

public class SendEmailMessages {
    public void sendMessage(String toEmail, String newPassword) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "805");

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("mindset.24.7.reset@gmail.com", "L0thmas1@");
            }
        });

        try {

            SMTPMessage message = new SMTPMessage(session);
            message.setFrom(new InternetAddress("mindset24-7"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("lothmas@live.com"));
            String htmlText = "<img src=https://localhost:8443/assets/img/logo-original.png/>";

            message.setSubject("MindSet24-7 Password Reset");
            message.setContent("Do not share below link with anyone<p></> Please follow link below to complete your password reset <p></> " +
                 "\n" +
                    "<a href=\"https://mindset24-7.co.za:8443/reset?password="+newPassword+"\">Click Here To Reset Your Password</a>\n"+"<p></>"+htmlText, "text/html; charset=utf-8");



//                message.setContent("This Is my First Mail Through Java");
            message.setNotifyOptions(SMTPMessage.NOTIFY_SUCCESS);
            int returnOption = message.getReturnOption();
            System.out.println(returnOption);
            Transport.send(message);
            System.out.println("sent");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}