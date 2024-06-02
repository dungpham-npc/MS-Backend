package com.cookswp.milkstore.service.post;

import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCodeException;
import com.cookswp.milkstore.pojo.dtos.PostModel.PostDTO;
import com.cookswp.milkstore.pojo.entities.Post;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.repository.PostRepository;
import com.cookswp.milkstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllVisibility();
    }

    @Override
    public Optional<Post> getPostByID(int id) {
        return postRepository.findByIDAndVisibility(id);
    }

    @Override
    public Post updatePost(int id, PostDTO post) {
        Optional<Post> postOptional = postRepository.findById(id);
        if(postOptional.isPresent()) {
            Post updatePost = postOptional.get();
            updatePost.setTitle(post.getTitle());
            updatePost.setContent(post.getContent());
            updatePost.setDateCreated(post.getDateCreated());
            updatePost.setUserComment(post.getUserComment());
            return postRepository.save(updatePost);
        } else {
            throw new RuntimeException("Not found post with the ID: " + id);
        }
    }

    @Override
    public Post createPost(PostDTO postRequest) {
        Post postEntity = new Post();
        User currentUser = userService.getCurrentUser();
        postEntity.setUserID(currentUser.getUserId());
        postEntity.setTitle(postRequest.getTitle());
        postEntity.setContent(postRequest.getContent());
        postEntity.setDateCreated(postRequest.getDateCreated());
        postEntity.setUserComment(postRequest.getUserComment());
        return postRepository.save(postEntity);
    }

    @Override
    public void deletePost(int id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            post.setVisibility(false);
            postRepository.save(post);
        }
    }
}
