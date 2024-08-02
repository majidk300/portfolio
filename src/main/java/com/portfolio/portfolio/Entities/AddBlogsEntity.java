package com.portfolio.portfolio.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class AddBlogsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int blogId;

    @Column(length = 200)
    @NotBlank(message = "Blog title is required")
    @Size(max = 200, message = "Blog title must be less than 200 characters")
    private String blogTitle;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    @NotBlank(message = "Blog post must be less than 3000 characters")
    @Size(message = "Blog post must be less than 3000 characters")
    private String blogPost;

    public AddBlogsEntity() {
    }

    public AddBlogsEntity(
            @NotBlank(message = "Blog title is required") @Size(max = 200, message = "Blog title must be less than 200 characters") String blogTitle,
            @NotBlank(message = "Blog post must be less than 3000 characters") @Size(message = "Blog post must be less than 3000 characters") String blogPost) {
        this.blogTitle = blogTitle;
        this.blogPost = blogPost;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public String getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(String blogPost) {
        this.blogPost = blogPost;
    }

    @Override
    public String toString() {
        return "AddBlogsEntity [blogId=" + blogId + ", blogTitle=" + blogTitle + ", blogPost=" + blogPost + "]";
    }

}
