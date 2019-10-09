package com.example.demo.blogpost;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogpostRepository extends CrudRepository<Blogpost, Long> {

    @Query("SELECT b FROM Blogpost b")
    @EntityGraph(Blogpost.COMMENTS_GRAPH)
    Iterable<Blogpost> findAllWithComments();
}
