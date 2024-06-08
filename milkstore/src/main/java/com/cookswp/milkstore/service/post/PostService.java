package com.cookswp.milkstore.service.post;

import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.PostModel.PostDTO;
import com.cookswp.milkstore.pojo.entities.Post;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.repository.PostRepository;
import com.cookswp.milkstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService {


    private final PostRepository postRepository;

    private final UserService userService;

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllVisibility();
    }

    @Override
    public Post getPostByID(int id) {
        Post post = postRepository.findByIDAndVisibility(id);
        if(post != null){
            return post;
        } else {
            throw new AppException(ErrorCode.POST_ID_NOT_FOUND);
        }
    }

    @Override
    public Post updatePost(int id, PostDTO postRequest) {
        Post postEntity = postRepository.findByIDAndVisibility(id);
        if(postEntity != null) {
            User currentUser = userService.getCurrentUser();
            postEntity.setUserID(currentUser.getUserId());
            postEntity.setContent(postRequest.getContent());
            postEntity.setTitle(postRequest.getTitle());
            postEntity.setDateCreated(new Date());
            return postRepository.save(postEntity);
        } else {
            throw new AppException(ErrorCode.POST_ID_NOT_FOUND);
        }
    }

    @Override
    public Post createPost(PostDTO postRequest) {
        Post postEntity = new Post();
        User currentUser = userService.getCurrentUser();
        postEntity.setUserID(currentUser.getUserId());
        postEntity.setTitle(postRequest.getTitle());
        postEntity.setContent(postRequest.getContent());
        postEntity.setDateCreated(new Date());
        postEntity.setUserComment("");
        return postRepository.save(postEntity);
    }

    @Override
    public void deletePost(int id) {
        Post postEntity = postRepository.findByIDAndVisibility(id);
        if(postEntity != null){
            postEntity.setVisibility(false);
            postRepository.save(postEntity);
        } else {
            throw new AppException(ErrorCode.POST_ID_NOT_FOUND);
        }
    }
}
