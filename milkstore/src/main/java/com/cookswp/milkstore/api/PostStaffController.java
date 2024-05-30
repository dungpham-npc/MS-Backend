package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.entities.Post;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post-staff")
public class PostStaffController {

    @Autowired
    private PostService postService;

    //CRUD post
    //create
    @PostMapping("/create-post")
    public ResponseData<Post> createPost(@RequestBody Post post) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "Post created successfully", postService.createPost(post));
    }

    //update
    @PatchMapping("/update-post/{ID}")
    public ResponseData<Post> updatePost(@PathVariable int ID, @RequestBody Post post) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Post updated successfully", postService.updatePost(ID, post));
    }

    //retrieve
    @GetMapping("/post")
    public ResponseData<List<Post>> getAllPost() {
        return new ResponseData<>(HttpStatus.OK.value(), "Post list", postService.getAllPosts());
    }

    @GetMapping("/get-post/{ID}")
    public ResponseData<Post> getPostById(@PathVariable int ID) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get post with ID: " + ID, postService.getPostByID(ID));
    }

}
