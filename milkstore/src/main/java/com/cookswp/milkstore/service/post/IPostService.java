package com.cookswp.milkstore.service.post;

import com.cookswp.milkstore.pojo.entities.Post;

import java.util.List;


public interface IPostService {

    //Get all post
   public List<Post> getAllPosts();

   //Get post by ID
   public Post getPostByID(int id);

   //Put post
    public Post updatePost(int id, Post post);

    //Create post
    public Post createPost(Post post);
}
