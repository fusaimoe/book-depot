package com.medusabookdepot.controller.files;

import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

	// Gmail username and password
    private static final String USERNAME = "oop15bookdepot";
    private static final String PASSWORD = "noteasytoguess";
    
    /* MAIN METHOD ONLY FOR TESTING PURPOSE
        public static void main(String[] args) throws MessagingException {
        String receiver = "andread251@gmail.com"; // list of recipient email addresses
        String subject = "Here are the exported files";
        String body = "Check if now it works with the correct name file!";
        String attachment = "prova.jpg";
        send(receiver, subject, body, attachment);
    }//*/

    private static void send(String receiver, String subject, String body, String attachment) throws MessagingException {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", USERNAME);
        props.put("mail.smtp.password", PASSWORD);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(props, null);

        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress(USERNAME+"@gmail.com"));

        message.setRecipients(Message.RecipientType.TO, receiver);

        message.setSubject(subject);

        BodyPart messageBodyPart = new MimeBodyPart();

        messageBodyPart.setText(body);

        Multipart multipart = new MimeMultipart();

        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();

        DataSource source = new FileDataSource(FileManager.getDirectoryPath()+attachment);

        messageBodyPart.setDataHandler(new DataHandler(source));

        messageBodyPart.setFileName(attachment);

        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        try {
            Transport tr = session.getTransport("smtps");
            tr.connect(host, USERNAME, PASSWORD);
            tr.sendMessage(message, message.getAllRecipients());
            System.out.println("Mail Sent Successfully");
            tr.close();

        } catch (SendFailedException sfe) {

            System.out.println(sfe);
        }
    }
}
