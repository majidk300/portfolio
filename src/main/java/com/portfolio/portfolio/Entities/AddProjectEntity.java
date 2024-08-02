package com.portfolio.portfolio.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Column;

@Entity
public class AddProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int projectId;


    @Column(length=250)
    @NotBlank(message="Project title is required")
    @Size(min = 3, max = 50, message = "Please enter a title between 3 and 50 characters")
    private String projectTitle;

    @Column(length=500)
    @NotEmpty
    private String projectDescription;

    
    @Column(length=250)
    @NotEmpty
    private String demoLink;

    
    @Column(length=250)
    @NotEmpty
    private String videoLink;

    
    @Column(length=250)
    @NotEmpty
    private String CodeLink;
 

    @Column(length=250)
    private String image;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        projectId = projectId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getDemoLink() {
        return demoLink;
    }

    public void setDemoLink(String demoLink) {
        this.demoLink = demoLink;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getCodeLink() {
        return CodeLink;
    }

    public void setCodeLink(String codeLink) {
        CodeLink = codeLink;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    

    public AddProjectEntity() {
    }

    public AddProjectEntity(
            @NotBlank(message = "Project title is required") @Size(min = 3, max = 50, message = "Please enter a title between 3 and 50 characters") String projectTitle,
            String projectDescription, String demoLink, String videoLink, String codeLink, String image) {
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
        this.demoLink = demoLink;
        this.videoLink = videoLink;
        CodeLink = codeLink;
        this.image = image;
    }


    
}