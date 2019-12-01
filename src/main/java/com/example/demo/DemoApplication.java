package com.example.demo;

import com.example.demo.blogpost.Blogpost;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.function.Consumer;

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

        Consumer<Blogpost> printBlogpost = bp -> {
            try {
                System.out.println("Blogpost: " + bp);
                System.out.println("#Comments: " + bp.getComments());
            } catch (LazyInitializationException lie) {
                System.out.println("Caught a LazyInitializationException since comments are lazily initialized.");
                System.out.println(lie.getMessage());
            }
        };

        bloggingService.getAllBlogpostsWithoutComments()
                .forEach(printBlogpost);

        bloggingService.getAllBlogpostsWithComments()
                .forEach(printBlogpost);
    }
}
