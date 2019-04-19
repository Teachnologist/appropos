package com.apropos.crypto.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import java.io.PrintStream;
import java.util.Properties;
import javax.mail.internet.*;

@Controller
@ComponentScan()
public class Email {

    private EmailConfig emailConfig = new EmailConfig();

    private String username = emailConfig.getUsername();
    private String password = emailConfig.getPassword();
    private String host = emailConfig.getHost();
    private Integer port = emailConfig.getPort();

    @Size(min=10,max=50)
    public String fromemail = "";

    public String getFromemail() {
        return fromemail;
    }

    @Size(min=10,max=50)
    public String tomemail = "";

    public String getTomemail() {
        return tomemail;
    }

    public void setTomemail(String tomemail) {
        this.tomemail = tomemail;
    }

    public void setFromemail(String fromemail) {
        this.fromemail = fromemail;
    }


    public String content = "";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String incomingMessage(String email, String body){
        String message = "Email: "+email+"\n"+
                "Sent the following message:\n"+
                body;
        return message;
    }

    public String outgoingMessage(String body){
        String message = "Thank you for contacting Jason Triche.\n"+
                         "He will respond in an appropriate time frame.\n"+
                       "You sent: "+
                body;
        return message;
    }

    public void sendEmail() {
        System.out.println("HOST: "+host+" PORT: "+port);

            //create sender

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        /*use header properties; remmeber to tell GMAIL what domain this is sending from*/
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.debug","true");

        //create mail instance
/*        SimpleMailMessage incomingmessage = new SimpleMailMessage();
        incomingmessage.setFrom(fromemail);
        incomingmessage.setTo(username);
        incomingmessage.setSubject("Incoming Message");
        incomingmessage.setText(incomingMessage(fromemail, content));
*/
       SimpleMailMessage outgoingmessage = new SimpleMailMessage();
        outgoingmessage.setFrom(username);
        outgoingmessage.setTo(fromemail);
        outgoingmessage.setSubject("Background mail test");
        outgoingmessage.setText(outgoingMessage(content));


//actually send it
     /*   mailSender.send(incomingmessage);*/
        mailSender.send(outgoingmessage);


    }

    public void sendResponseEmail() {
        System.out.println("HOST: "+host+" PORT: "+port);

        //create sender

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        /*use header properties; remmeber to tell GMAIL what domain this is sending from*/
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol","smtp");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.debug","true");

        //create mail instance
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(fromemail);
        message.setSubject("Response Message");
        message.setText(content);


//actually send it
        mailSender.send(message);

    }



}
