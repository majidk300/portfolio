package com.portfolio.portfolio.controllers;

import java.security.Principal;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;


import com.portfolio.portfolio.Entities.AddBlogsEntity;
import com.portfolio.portfolio.Entities.AddProjectEntity;
import com.portfolio.portfolio.dao.SaveBlogsRepository;
import com.portfolio.portfolio.dao.UserRepository;
import com.portfolio.portfolio.dao.addProjectsRepository;
import com.portfolio.portfolio.services.EmailOtpService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    Random random = new Random(10000000);

    @Autowired
    private EmailOtpService emailOtpService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private addProjectsRepository addProjects;

    @Autowired
    private SaveBlogsRepository fetchBlogs;

    // Home Page Handler
    @RequestMapping("/")
    public String home(Model m) {
        m.addAttribute("title", "Home - Majid Naseem");
        return "index";
    }

    // Resume Page Handler
    @RequestMapping("/resume")
    public String resume(Model m) {
        m.addAttribute("title", "Resume - Majid Naseem");
        return "Resume";
    }

    // Projects Page Handler
    @RequestMapping("/projects")
    public String projects(Model m) {
        m.addAttribute("title", "Projects - Majid Naseem");
        return "Projects";
    }

    // Blogs page Handler
    @RequestMapping("/blogs")
    public String blogs(Model m) {
        m.addAttribute("title", "Blogs - Majid Naseem");
        return "Blogs";
    }

    // Contact page Handler
    @RequestMapping("/contact")
    public String contact(Model m) {
        m.addAttribute("title", "Contact - Majid Naseem");
        return "Contact";
    }

    // Admin Login Page Handler
    @RequestMapping("/admin")
    public String admin(Model m) {
        m.addAttribute("title", "Admin login - Majid Naseem");

        return "admin";
    }

    // Show projects handler with pagination
    @GetMapping("/projects/{page}")
    public String showProjects(@PathVariable("page") Integer page, Model m) {
        m.addAttribute("title", "Show Projects - Majid Naseem");

        Pageable pageable = PageRequest.of(page, 8);
        Page<AddProjectEntity> projects = this.addProjects.findAllOrderByProjectIdDesc(pageable);

        m.addAttribute("projects", projects);
        m.addAttribute("currentPage", page);
        m.addAttribute("totalPage", projects.getTotalPages());

        return "Projects";
    }

    // show blog posts handler
    @GetMapping("/blogs/{page}")
    public String blogs(@PathVariable("page") Integer page, Model m) {

        m.addAttribute("title", "Show Blogs - Majid Naseem");

        Pageable pageable = PageRequest.of(page, 12);

        Page<AddBlogsEntity> blogs = this.fetchBlogs.findAllOrderByBlogsIdDesc(pageable);

        m.addAttribute("blogs", blogs);
        m.addAttribute("currentPage", page);
        m.addAttribute("totalPage", blogs.getTotalPages());

        return "Blogs";
    }

    // showing blog post
    @GetMapping("/showPost/{blogId}")
    public String showPost(@PathVariable("blogId") Integer blogId, Model m) {

        AddBlogsEntity readBlogs = this.fetchBlogs.findByblogId(blogId);

        m.addAttribute("title", readBlogs.getBlogTitle());

        m.addAttribute("readBlogs", readBlogs);

        return "readBlogs";

    }

    // logout handler
    @GetMapping("/logout")
    public String logout(Model model, HttpServletRequest request, HttpServletResponse response) {
        // Perform custom logout logic if needed

        // Invalidate session and remove cookies
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        // Add a logout message to the model
        model.addAttribute("logoutAlert", "Successfully logged out!!");

        // Redirect to the admin page or another view
        return "admin";
    }
    


}
