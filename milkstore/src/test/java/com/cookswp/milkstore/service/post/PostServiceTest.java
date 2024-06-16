package com.cookswp.milkstore.service.post;

import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.exception.ErrorCode;
import com.cookswp.milkstore.pojo.entities.Post;
import com.cookswp.milkstore.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void testGetPostByID() {
        Post mockPost = new Post();
        mockPost.setContent("Mock Content");
        mockPost.setTitle("Mock Title");

        when(postRepository.findByIDAndVisibility(1)).thenReturn(mockPost);

        Post post = postService.getPostByID(1);

        assertEquals(mockPost.getContent(), post.getContent());
        assertEquals(mockPost.getTitle(), post.getTitle());
    }

    @Test
    void testGetPostByID_NotFound() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(null);

        Exception exception = assertThrows(AppException.class, () -> {
            postService.getPostByID(1);
        });

        String expectedMessage = exception.getMessage();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetAllPost(){
        List<Post> mockList = new ArrayList<>();
        mockList.add(new Post());
        mockList.add(new Post());
        when(postRepository.findAllVisibility()).thenReturn(mockList);

        List<Post> postList = postService.getAllPosts();

        assertEquals(mockList.size(), postList.size());
    }

}