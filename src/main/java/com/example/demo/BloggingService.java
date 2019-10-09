package com.example.demo;

import com.example.demo.blogpost.Blogpost;
import com.example.demo.blogpost.BlogpostRepository;
import com.example.demo.comment.Comment;
import com.example.demo.comment.CommentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BloggingService {

    private final BlogpostRepository blogpostRepository;
    private final CommentRepository commentRepository;

    public BloggingService(BlogpostRepository blogpostRepository, CommentRepository commentRepository) {
        this.blogpostRepository = blogpostRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Blogpost newPost() {
        Blogpost blogpost = new Blogpost();
        return blogpostRepository.save(blogpost);
    }

    @Transactional
    public Blogpost addComment(Long blogpostId, String text) {
        Blogpost blogpost = blogpostRepository.findById(blogpostId).get();

        Comment comment = new Comment();
        comment.setText(text);
        comment.setBlogpost(blogpost);
        commentRepository.save(comment);

        return blogpost;
    }

    public Iterable<Blogpost> getAllBlogpostsWithComments() {
        return blogpostRepository.findAllWithComments();

    }

    public Iterable<Blogpost> getAllBlogposts() {
        return blogpostRepository.findAll();
    }
}
