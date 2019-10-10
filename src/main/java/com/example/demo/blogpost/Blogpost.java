package com.example.demo.blogpost;


import com.example.demo.comment.Comment;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Blogpost")
@NamedEntityGraph(name = Blogpost.COMMENTS_GRAPH, attributeNodes = @NamedAttributeNode("comments"))
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Builder
public class Blogpost {

    public static final String COMMENTS_GRAPH = "Blogpost.comments";

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToMany(mappedBy = "blogpost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
