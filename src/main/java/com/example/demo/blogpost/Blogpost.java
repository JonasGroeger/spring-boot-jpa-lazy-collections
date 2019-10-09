package com.example.demo.blogpost;


import com.example.demo.BloggingService;
import com.example.demo.comment.Comment;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Blogpost")
@NamedEntityGraph(name = Blogpost.COMMENTS_GRAPH, attributeNodes = @NamedAttributeNode("comments"))
public class Blogpost {


    public static final String COMMENTS_GRAPH = "Blogpost.comments";

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToMany(mappedBy = "blogpost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    public Blogpost() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setBlogpost(this);
    }
}
