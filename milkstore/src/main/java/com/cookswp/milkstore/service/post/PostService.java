package com.cookswp.milkstore.service.post;

import com.cookswp.milkstore.pojo.entities.Post;
import com.cookswp.milkstore.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllVisibility();
    }

    @Override
    public Optional<Post> getPostByID(int id) {
        return postRepository.findByIDAndVisibility(id);
    }

    @Override
    public Post updatePost(int id, Post post) {
        Optional<Post> postOptional = postRepository.findById(id);
        if(postOptional.isPresent()) {
            Post updatePost = postOptional.get();
            updatePost.setUserID(post.getUserID());
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
    public Post createPost(Post post) {
        return postRepository.save(post);
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
