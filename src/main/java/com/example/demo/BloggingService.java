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
        Blogpost blogpost = Blogpost.builder()
                .build();
        return blogpostRepository.save(blogpost);
    }

    @Transactional
    public Blogpost addComment(Long blogpostId, String text) {
        return blogpostRepository.findById(blogpostId).map(blogpost -> {
            Comment comment = Comment.builder()
                    .text(text)
                    .blogpost(blogpost)
                    .build();
            commentRepository.save(comment);
            return blogpost;
        }).orElseThrow(RuntimeException::new);
    }

    Iterable<Blogpost> getAllBlogpostsWithComments() {
        return blogpostRepository.findAllWithComments();
    }

    Iterable<Blogpost> getAllBlogpostsWithoutComments() {
        return blogpostRepository.findAll();
    }
}
