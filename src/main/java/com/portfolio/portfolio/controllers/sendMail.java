package com.portfolio.portfolio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.portfolio.portfolio.services.EmailOtpService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class sendMail {

    @Autowired
    private EmailOtpService emailService;
    

    @PostMapping("/sendMail")
    public String sendMail(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("message") String message, HttpSession session, Model m){

        System.out.println("This is "+name);
        System.out.println("This is "+email);
        System.out.println("This is "+message);
        
        

        try {
            String subject = name;
            String mg = ""
                + "<div style='border:1px solid #e2e2e2; padding:20px'>"
                + "<h3>"
                + "Email : "+email+"<br>"
                + "<b> " + message +""
                + "</b>"
                + "</h3>" 
                + "</div>";

                String to = "majidnaseem726@gmail.com";

                boolean flag = this.emailService.sendMails(subject, mg, to);

                
                if(flag){

                    m.addAttribute("msg", "Email successfully send !!");
                    return "Contact";
                }else{
                    m.addAttribute("msg", "Email not send !! please try again");
                    return "Contact";
                }


            
        } catch (Exception e) {
            e.getStackTrace();
            m.addAttribute("msg", "Email not send Try !! please try again");
        }


        return "Contact";
        
    }
    
}
