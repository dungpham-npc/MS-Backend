package com.cookswp.milkstore.repository;

import com.cookswp.milkstore.pojo.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query("SELECT p FROM Post p WHERE p.visibility = true")
    List<Post> findAllVisibility();

    @Query("SELECT p FROM Post p WHERE p.id = :id AND p.visibility = true")
    Optional<Post> findByIDAndVisibility(@Param("id") int id);
}
