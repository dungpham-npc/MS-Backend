package com.cookswp.milkstore.service.post;

import com.cookswp.milkstore.mapper.PostMapper;
import com.cookswp.milkstore.pojo.dtos.PostModel.PostDTO;
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

    private final PostMapper postMapper = PostMapper.INSTANCE;

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
    /*
    *  Post postEntity = new Post();
        postEntity.setUserID(postRequest.getUserID());
        postEntity.setTitle(postRequest.getTitle());
        postEntity.setContent(postRequest.getContent());
        postEntity.setDateCreated(postRequest.getDateCreated());
        postEntity.setUserComment(postRequest.getUserComment());
        return postRepository.save(postEntity);
    * */
    @Override
    public Post createPost(PostDTO postRequest) {
        Post postEntity = postMapper.toEntity(postRequest);
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
