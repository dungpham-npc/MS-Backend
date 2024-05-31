package com.cookswp.milkstore.api;

import com.cookswp.milkstore.pojo.dtos.PostModel.PostDTO;
import com.cookswp.milkstore.pojo.entities.Post;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post-staff")
public class PostStaffController {

    @Autowired
    private PostService postService;

    //create
    @PostMapping("/create-post")
    public ResponseData<Post> createPost(@RequestBody PostDTO postRequest) {
        return new ResponseData<>(HttpStatus.CREATED.value(), "Post created successfully", postService.createPost(postRequest));
    }

    //update
    @PatchMapping("/update-post/{ID}")
    public ResponseData<Post> updatePost(@PathVariable int ID, @RequestBody PostDTO postRequest) {
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "Post updated successfully", postService.updatePost(ID, postRequest));
    }

    //retrieve
    @GetMapping("/post")
    public ResponseData<List<Post>> getAllPost() {
        return new ResponseData<>(HttpStatus.OK.value(), "Post list", postService.getAllPosts());
    }

    @GetMapping("/get-post/{ID}")
    public ResponseData<Optional<Post>> getPostById(@PathVariable int ID) {
        return new ResponseData<>(HttpStatus.OK.value(), "Get post with ID: " + ID, postService.getPostByID(ID));
    }

    //Delete Post
    @PatchMapping("/delete-post/{ID}")
    public ResponseData<Post> deletePost(@PathVariable int ID){
        postService.deletePost(ID);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete post with ID: " + ID);
    }

}
