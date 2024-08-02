package com.portfolio.portfolio.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

@Service
public class EmailOtpService {

    public boolean sendOtp(String subject, String message, String to)
    {
        boolean f = false;

        String from = "majidnaseem100@gmail.com";

        //VAriable for gmail
        String host="smtp.gmail.com";

        // Get System Properties
        Properties properties = System.getProperties();
        System.out.println("Properties "+properties);

        // Setting host set

        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth","true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(from, "dpxj xcef ptwf jelp");
            }
        });

        session.setDebug(true);

        // compose the message [text,multi media]
        MimeMessage m = new MimeMessage(session);

        try{

            //from email
		m.setFrom(from);
		
		//adding recipient to message
		m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		
		//adding subject to message
		m.setSubject(subject);

        //adding text to message
		// m.setText(message);
		m.setContent(message,"text/html");

        Transport.send(m);

        System.out.println("Sent success...................");

        f=true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return f; 
    }
    
}
