package com.cookswp.milkstore.service.post;

import com.cookswp.milkstore.exception.AppException;
import com.cookswp.milkstore.pojo.dtos.PostModel.PostDTO;
import com.cookswp.milkstore.pojo.entities.Post;
import com.cookswp.milkstore.repository.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private PostDTO dto;
    private Post post;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setTitle("Entity title");
        post.setContent("Entity content");
        //======================//
        dto = new PostDTO();
        dto.setTitle("DTO Title");
        dto.setContent("DTO Content");
    }

    @Test
    void testCreatePostSuccess() {
        Post postEntity = new Post();
        postEntity.setContent("DTO Content");
        postEntity.setTitle("DTO Title");
        when(postRepository.save(any(Post.class))).thenReturn(postEntity);
        Post createdPost = postService.createPost(dto);
        assertNotNull(createdPost);
        assertEquals(dto.getTitle(), createdPost.getTitle());
        assertEquals(dto.getContent(), createdPost.getContent());
    }

    @Test
    void testCreatePostFailCaseNullTitleShouldThrowException() {
        dto.setTitle(null);
        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(dto);
        });
        assertEquals(exception.getMessage(), "Post title can not be empty");
    }

    @Test
    void testCreatePostFailCaseEmptyTitleShouldThrowException() {
        dto.setTitle("");
        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(dto);
        });
        assertEquals(exception.getMessage(), "Post title can not be empty");
    }

    @Test
    void testCreatePostFailCaseBlankTitleShouldThrowException() {
        dto.setTitle(" ");
        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(dto);
        });
        assertEquals(exception.getMessage(), "Post title can not be empty");
    }

    @Test
    void testCreatePostFailCaseEmptyContentShouldThrowException() {
        dto.setContent("");
        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(dto);
        });
        assertEquals(exception.getMessage(), "Post content can not be empty");
    }

    @Test
    void testCreatePostFailCaseNullContentShouldThrowException() {
        dto.setContent(null);
        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(dto);
        });
        assertEquals(exception.getMessage(), "Post content can not be empty");
    }

    @Test
    void testCreatePostFailCaseBlankContentShouldThrowException() {
        dto.setContent(" ");
        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(dto);
        });
        assertEquals(exception.getMessage(), "Post content can not be empty");
    }

    @Test
    void testUpdatePostSuccess() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post updateDTO = postService.updatePost(1, dto);

        assertNotNull(updateDTO);
        assertEquals(dto.getTitle(), updateDTO.getTitle());
        assertEquals(dto.getContent(), updateDTO.getContent());
    }

    @Test
    void testUpdatePostFailCaseBlankContentShouldThrowException() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(post);

        dto.setContent(" ");
        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, dto);
        });
        assertEquals(exception.getMessage(), "Post content can not be empty");
    }

    @Test
    void testUpdatePostFailCaseNullContentShouldThrowException() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(post);

        dto.setContent(null);

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, dto);
        });
        assertEquals(exception.getMessage(), "Post content can not be empty");
    }

    @Test
    void testUpdatePostFailCaseEmptyContentShouldThrowException() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(post);

        dto.setContent("");

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, dto);
        });
        assertEquals(exception.getMessage(), "Post content can not be empty");
    }

    @Test
    void testUpdatePostFailCaseBlankTitleShouldThrowException() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(post);

        dto.setTitle(" ");

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, dto);
        });
        assertEquals(exception.getMessage(), "Post title can not be empty");
    }

    @Test
    void testUpdatePostFailCaseNullTitleShouldThrowException() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(post);

        dto.setTitle(null);

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, dto);
        });
        assertEquals(exception.getMessage(), "Post title can not be empty");
    }

    @Test
    void testUpdatePostFailCaseEmptyTitleShouldThrowException() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(post);

        dto.setTitle("");

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, dto);
        });
        assertEquals(exception.getMessage(), "Post title can not be empty");
    }


    @Test
    void testDeletePostSuccess() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(post);

        assertAll(() -> {//To handle void method in service we are using assertAll
            postService.deletePost(1);
        });
    }

    @Test
    void testDeletePostFailShouldThrowException() {
        AppException exception = assertThrows(AppException.class, () -> {
            postService.deletePost(1);
        });

        assertEquals(exception.getMessage(), "Post id not found");
    }

    @Test
    void testGetPostByIDSuccess() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(post);
        assertNotNull(postService.getPostByID(1));
    }

    @Test
    void testGetPostByIDFail() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> {
            postService.getPostByID(1);
        });

        assertEquals(exception.getMessage(), "Post id not found");
    }

    @Test
    void testGetAllPostSuccess() {
        List<Post> list = new ArrayList<>();

        Post post = Post.builder()
                .title("LIST TITLE 1")
                .content("LIST CONTENT 1")
                .build();
        Post post2 = Post.builder()
                .title("LIST TITLE 2")
                .content("LIST CONTENT 2")
                .build();

        list.add(post);
        list.add(post2);


        when(postRepository.findAllVisibility()).thenReturn(list);

        List<Post> posts = postService.getAllPosts();

        assertNotNull(posts);
        assertEquals(2, posts.size());
        assertEquals(post.getTitle(), posts.get(0).getTitle());
        assertEquals(post.getContent(), posts.get(0).getContent());
    }

    @Test
    void testGetAllPostFail() {
        List<Post> list = new ArrayList<>();
        when(postRepository.findAllVisibility()).thenReturn(list);

        AppException exception = assertThrows(AppException.class, () -> {
            postService.getAllPosts();
        });

        assertEquals(0, list.size());
        assertEquals(exception.getMessage(), "Haven't any posts yet");
    }

    @Test
    void testCreatePostTitleMustBeUnique() {
        PostDTO entity = PostDTO.builder()
                .title("Entity title")
                .content("Entity content")
                .build();

        when(postRepository.existsByTitle("Entity title")).thenReturn(true);

        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(entity);
        });

        assertEquals("Post title must be unique", exception.getMessage());
    }
        ///Jira test #3
}