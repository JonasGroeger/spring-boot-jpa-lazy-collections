package com.example.demo;

import com.example.demo.blogpost.Blogpost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class DemoApplication {

    private final BloggingService bloggingService;

    @Autowired
    public DemoApplication(BloggingService bloggingService) {
        this.bloggingService = bloggingService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @PostConstruct
    public void init() {
        Blogpost blogpost = bloggingService.newPost();
        bloggingService.addComment(blogpost.getId(), "Foo");
        bloggingService.addComment(blogpost.getId(), "Bar");
        bloggingService.addComment(blogpost.getId(), "Zap");

        bloggingService.getAllBlogposts().forEach(bp -> {
            System.out.println("Blogpost: " + bp);
            /*
             * Fails with:
             * Caused by: org.hibernate.LazyInitializationException:
             *     failed to lazily initialize a collection of role:
             *         com.example.demo.blogpost.Blogpost.comments, could not initialize proxy - no Session
             */
            // System.out.println("#Comments: " + bp.getComments());
        });

        bloggingService.getAllBlogpostsWithComments().forEach(bp -> {
            System.out.println("Blogpost: " + bp);
            System.out.println("#Comments: " + bp.getComments());
        });
    }
}
