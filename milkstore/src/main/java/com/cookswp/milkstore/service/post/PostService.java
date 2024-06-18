package com.cookswp.milkstore.service.post;

import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.dtos.PostModel.PostDTO;
import com.cookswp.milkstore.pojo.entities.Post;
import com.cookswp.milkstore.repository.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService implements IPostService {

    private final PostRepository postRepository;
    private Post postEntity = new Post();

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = postRepository.findAllVisibility();
        if (!posts.isEmpty()) {
            return posts;
        } else {
            throw new AppException(ErrorCode.ALL_POST_EMPTY);
        }
    }

    @Override
    public Post getPostByID(int id) {
        Post post = postRepository.findByIDAndVisibility(id);
        if (post != null) {
            return post;
        } else {
            throw new AppException(ErrorCode.POST_ID_NOT_FOUND);
        }
    }

    @Override
    public Post updatePost(int ID, PostDTO postRequest) {
        postEntity = postRepository.findByIDAndVisibility(ID);                      //Find post with ID
        if (postEntity == null) throw new AppException(ErrorCode.POST_ID_NOT_FOUND);//Throw exception
        validateInputRequest(postRequest);                                          //Valid null empty blank
        postEntity.setUserID(7);                                                    //setUserID is not complete
        postEntity.setContent(postRequest.getContent());                            //set content from the request DTO
        postEntity.setTitle(postRequest.getTitle());                                //set title from the request DTO
        postEntity.setDateCreated(new Date());                                      //set new date when create
        return postRepository.save(postEntity);                                     //Save into DB
    }

    @Override
    public Post createPost(PostDTO postRequest) {
        validateInputRequest(postRequest);                  //Valid null empty blank
        postEntity.setUserID(7);                            //setUserID is not complete
        postEntity.setTitle(postRequest.getTitle());        //create new title
        postEntity.setContent(postRequest.getContent());    //create new content
        postEntity.setDateCreated(new Date());              //create when create post
        postEntity.setUserComment("");                      //set user comment to empty for the user can comment in the post
        return postRepository.save(postEntity);
    }

    private void validateInputRequest(PostDTO postRequest) {
        if (postRequest.getTitle() == null || postRequest.getTitle().isBlank() || postRequest.getTitle().isEmpty()) {
            throw new AppException(ErrorCode.POST_TITLE_ERROR);
        }
        if (postRequest.getContent() == null || postRequest.getContent().isBlank() || postRequest.getContent().isEmpty()) {
            throw new AppException(ErrorCode.POST_CONTENT_ERROR);
        }
    }

    @Override
    public void deletePost(int id) {
        Post postEntity = postRepository.findByIDAndVisibility(id);
        if (postEntity != null) {
            postEntity.setVisibility(false);
            postRepository.save(postEntity);
        } else {
            throw new AppException(ErrorCode.POST_ID_NOT_FOUND);
        }
    }
}
