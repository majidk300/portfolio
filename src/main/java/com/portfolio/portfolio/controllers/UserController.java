package com.portfolio.portfolio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.portfolio.portfolio.Entities.AddBlogsEntity;
import com.portfolio.portfolio.Entities.AddProjectEntity;
import com.portfolio.portfolio.Entities.adminEntity;
import com.portfolio.portfolio.dao.SaveBlogsRepository;
import com.portfolio.portfolio.dao.UserRepository;
import com.portfolio.portfolio.dao.addProjectsRepository;
import com.portfolio.portfolio.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/adminAccess")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private addProjectsRepository addProjects;

    @Autowired
    private SaveBlogsRepository saveBlogs;

    @Autowired
    private SaveBlogsRepository fetchBlogs;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String userName = principal.getName();
        System.out.println("Username: " + userName);
        adminEntity projects = this.userRepository.getUserByUserName(userName);
        System.out.println("Projects: " + projects);
        model.addAttribute("Projects", projects);
    }

    @GetMapping("/admin_dashboard")
    public String dashboard(Model m) {
        m.addAttribute("title", "dashboard - Majid Naseem");
        return "admin/admin-dashboard";
    }

    @GetMapping("/dashboard")
    public String Admin_dashboard(Model m) {

        m.addAttribute("title", "dashboard - Majid Naseem");

        return "admin/dashboard";

    }

    @GetMapping("/addProjects")
    public String addProjects(Model m) {
        m.addAttribute("title", "Add Projects - Majid Naseem");
        m.addAttribute("SaveProject", new AddProjectEntity());
        return "admin/addProjects";
    }

    @PostMapping("/save-projects")
    public String saveProjects(@Valid @ModelAttribute AddProjectEntity SaveProject, BindingResult result, Model m,
            @RequestParam("profileImage") MultipartFile file, Principal principal, HttpSession session) {

        try {
            if (result.hasErrors()) {
                System.out.println("Error: " + result.toString());
                m.addAttribute("SaveProject", SaveProject);
                return "admin/addProjects";
            }

            // String one = SaveProject.getCodeLink();
            // String two = SaveProject.getDemoLink();
            // String three = SaveProject.getVideoLink();
            // String four = SaveProject.getProjectDescription();
            // String five = SaveProject.getProjectTitle();

            // System.out.println(one);
            // System.out.println(two);
            // System.out.println(three);
            // System.out.println(four);
            // System.out.println(five);
            // System.out.println(file.getOriginalFilename());

            String name = principal.getName();
            adminEntity user = this.userRepository.getUserByUserName(name);

            if (file.isEmpty()) {
                System.out.println("File is Empty");
                SaveProject.setImage("thumnail.jpg");
            } else {
                SaveProject.setImage(file.getOriginalFilename());

                File saveDir = new ClassPathResource("static/projectImage/").getFile();

                if (!saveDir.exists()) {
                    // Create the directory if it doesn't exist
                    boolean dirCreated = saveDir.mkdirs();
                    if (dirCreated) {
                        System.out.println("Directory created successfully.");
                    } else {
                        System.out.println("Failed to create directory.");
                    }
                } else {
                    System.out.println("Directory already exists.");
                }

                Path path = Paths.get(saveDir.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Image is uploaded");
                System.out.println("PATH IS : " + path);
            }
            this.addProjects.save(SaveProject);
            System.out.println("Data: " + SaveProject);
            System.out.println("Added to database");

            // message success
            session.setAttribute("message", new Message("Project successfully added !! Add more... ", "success"));

        } catch (Exception e) {
            e.printStackTrace();
            // message success
            session.setAttribute("message", new Message("Something went wrong !! Try again... ", "danger"));
            System.out.println("Error: " + e.getMessage());
        }

        return "admin/addProjects";
    }

    // show-all-projects handler
    @GetMapping("/show_projects")
    public String show_projects(Model m) {

        m.addAttribute("title", "Show All Projects");

        return "admin/show-all-projects";

    }

    // //projects handler
    @GetMapping("/show_projects/{page}")
    public String projects_access(@PathVariable("page") Integer page, Model m) {

        m.addAttribute("title", "Projects - Majid Naseem");

        Pageable pageable = PageRequest.of(page, 12);

        Page<AddProjectEntity> projects = this.addProjects.findAllOrderByProjectIdDesc(pageable);

        m.addAttribute("projects", projects);
        m.addAttribute("currentPage", page);
        m.addAttribute("totalPage", projects.getTotalPages());

        return "admin/show-all-projects";

    }

    // Delete Projects handler
    @GetMapping("/delete_Projects/{deleteId}")
    public String deleteProjects(@PathVariable("deleteId") Integer deleteId, Model m, HttpSession session) {

        try {

            this.addProjects.deleteById(deleteId);

            System.out.println("DELETED : " + deleteId);

            session.setAttribute("message", new Message("Project successfully deleted", "success"));

            return "redirect:/adminAccess/show_projects/0";

        } catch (Exception e) {
            e.printStackTrace();

            session.setAttribute("message", new Message("Something went wrong !! Please try  again ", "danger"));

            System.out.println(e.getMessage());
        }

        return "redirect:/adminAccess/show_projects/0";

    }

    // Find Project for update projects handler
    @GetMapping("/updateProject/{projectId}")
    public String updateProject(@PathVariable("projectId") Integer projectId, Model m, HttpSession session) {

        m.addAttribute("title", "Update - Majid Naseem");

        AddProjectEntity Singleprojects = addProjects.findByProjectId(projectId);

        if (Singleprojects != null) {
            System.out.println("Project  is " + projectId);
            m.addAttribute("Singleprojects", Singleprojects);

        } else {

            session.setAttribute("message", new Message("Project not found", "danger"));
            return "redirect:/adminAccess/show_projects/0";

        }

        return "admin/updateProjects";

    }

    // Update project handler
    @PostMapping("/updateProject")
    public String updateProject(@Valid @ModelAttribute AddProjectEntity updateEntity, BindingResult result,
            @RequestParam("profileImage") MultipartFile file, @RequestParam("ProjectID") Integer ProjectID,
            Principal principal, HttpSession session, Model m) {

        try {

            if (result.hasErrors()) {
                System.out.println("Error: " + result.toString());
                m.addAttribute("Singleprojects", updateEntity);
                return "admin/updateProjects";
            }

            // Fetch the existing project from the database
            AddProjectEntity existingProject = addProjects.findByProjectId(ProjectID);

            if (existingProject == null) {
                session.setAttribute("message", new Message("Project not found !! Try again... ", "danger"));
                return "redirect:/adminAccess/show_projects/0";
            }

            // Update the existing project fields
            existingProject.setProjectTitle(updateEntity.getProjectTitle());
            existingProject.setDemoLink(updateEntity.getDemoLink());
            existingProject.setVideoLink(updateEntity.getVideoLink());
            existingProject.setCodeLink(updateEntity.getCodeLink());
            existingProject.setProjectDescription(updateEntity.getProjectDescription());

            if (file.isEmpty()) {
                System.out.println("File is empty");

                existingProject.setImage("thumnail.jpg");

            } else {
                existingProject.setImage(file.getOriginalFilename());

                File saveDir = new ClassPathResource("static/projectImage/").getFile();

                Path path = Paths.get(saveDir.getAbsolutePath() + File.separator + file.getOriginalFilename());

                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Image is uploaded");
                System.out.println("PATH IS : " + path);

            }

            this.addProjects.save(existingProject);

            System.out.println("Data: " + existingProject);
            System.out.println("Added to database");

            // message success
            session.setAttribute("message", new Message("Project successfully updated !! Add more... ", "success"));

            return "redirect:/adminAccess/show_projects/0";

        } catch (Exception e) {
            e.printStackTrace();

            session.setAttribute("message", new Message("Something went wrong !! Try again... ", "danger"));
            System.out.println("Error: " + e.getMessage());
        }

        return "redirect:/adminAccess/show_projects/0";

    }

    // addBlogs handler
    @GetMapping("/addBlogs")
    public String addBlogs(Model m) {

        m.addAttribute("title", "Add Blogs - Majid Naseem");
        return "/admin/addBlogs";

    }

    @PostMapping("/saveBlogPost")
    public String saveBlogPost(@Valid @ModelAttribute AddBlogsEntity addBlogs, BindingResult result,
            HttpSession session, Model m) {

        String t = addBlogs.getBlogTitle();
        String p = addBlogs.getBlogPost();

        System.out.println("This is post heading : " + t);
        System.out.println("This is post content : " + p);

        try {

            m.addAttribute("title", "Add Blogs - Majid Naseem");

            if (result.hasErrors()) {

                m.addAttribute("addBlogs", addBlogs);
                session.setAttribute("message", new Message("Something  went wrong please try  again !! ", "danger"));

                for (ObjectError error : result.getAllErrors()) {
                    System.out.println("ERROR MESSAGE: " + error.getDefaultMessage());
                }

                return "admin/addBlogs";
            }

            this.saveBlogs.save(addBlogs);

            session.setAttribute("message", new Message("Blog successfully posted !! Add more... ", "success"));

            return "admin/addBlogs";

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Message : " + e.getMessage());
        }

        return "admin/addBlogs";
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

        return "admin/showAllBlogs";
    }

    // delete blogs handler
    @GetMapping("/deleteBlog/{deleteId}")
    public String deleteBlog(@PathVariable("deleteId") Integer deleteId, Model m, HttpSession session) {

        try {

            this.fetchBlogs.deleteById(deleteId);

            System.out.println("DELETED : " + deleteId);

            m.addAttribute("title", "show all blogs -  Majid NAseem");

            session.setAttribute("message", new Message("Blogs sucessfully deleted", "success"));

            return "redirect:/adminAccess/blogs/0";

        } catch (Exception e) {
            e.printStackTrace();

            session.setAttribute("message", new Message("Something went wrong !! Please try  again ", "danger"));

            System.out.println(e.getMessage());
        }

        return "redirect:/adminAccess/blogs/0";

    }


    //fetch update blogs handler
    @GetMapping("/updateBlog/{updateId}")
    public String updateBlog(@PathVariable("updateId") Integer updateId,  Model m, HttpSession session){


        m.addAttribute("title", "update blogs - Majid Naseem");

        AddBlogsEntity addBlogsEntity = this.fetchBlogs.findByblogId(updateId);

        if(addBlogsEntity!=null){

            m.addAttribute("blogsData",addBlogsEntity);
            System.out.println("Project is "+addBlogsEntity.getBlogId());

            return "admin/singleBlogs";

        }else{

            session.setAttribute("message", new Message("Blog not found !! ", "danger"));

        }

        return "redirect:/adminAccess/blogs/0";



    }

    // update blog handler
    @PostMapping("/updateBlogPost")
    public String String (@Valid @ModelAttribute AddBlogsEntity updateBlog, BindingResult result , HttpSession session, Model m){


        System.out.println(updateBlog.getBlogId());
        System.out.println(updateBlog.getBlogTitle());
        System.out.println(updateBlog.getBlogPost());

        m.addAttribute("title", "update blog - Majid Naseem");
        
        try {

            int bID  = updateBlog.getBlogId();

            if(result.hasErrors()){

                session.setAttribute("message", new Message("Something went wrong please try again !! ","danger"));
                m.addAttribute("blogsData", updateBlog);
                return "redirect:/adminAccess/updateBlog/bID";

            }

            AddBlogsEntity existingBlog = this.fetchBlogs.findByblogId(bID);

            if(existingBlog==null){

                session.setAttribute("message", new Message("Blog post not found please try again !! ","danger"));
                return "redirect:/adminAccess/blogs/0";

            }

            // Update the existing project fields
            existingBlog.setBlogTitle(updateBlog.getBlogTitle());
            existingBlog.setBlogPost(updateBlog.getBlogPost());

            this.fetchBlogs.save(existingBlog);

            session.setAttribute("message", new Message("Blog successfully updated !!","success"));

            return "redirect:/adminAccess/blogs/0";

            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR : "+e.getMessage());
        }

        return "redirect:/adminAccess/blogs/0";
     
    }

}
