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


    @Test
    void testCreatePostSuccess() {
        Post postEntity = Post.builder()
                .title("Title")
                .content("Content")
                .build();
        PostDTO postDTO = PostDTO.builder()
                .title("Title")
                .content("Content")
                .build();
        when(postRepository.save(any(Post.class))).thenReturn(postEntity);
        Post createdPost = postService.createPost(postDTO);
        assertNotNull(createdPost);
        assertEquals(postDTO.getTitle(), createdPost.getTitle());
        assertEquals(postDTO.getContent(), createdPost.getContent());
    }

    @Test
    void testCreatePostFailCaseNullTitleShouldThrowException() {
        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(PostDTO.builder()
                    .title(null)
                    .content("Content")
                    .build());
        });
        assertEquals(exception.getMessage(), "Post title can not be empty");
    }

    @Test
    void testCreatePostFailCaseEmptyTitleShouldThrowException() {
        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(PostDTO.builder()
                    .title("")
                    .content("Content")
                    .build()
            );
        });
        assertEquals(exception.getMessage(), "Post title can not be empty");
    }

    @Test
    void testCreatePostFailCaseBlankTitleShouldThrowException() {
        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(PostDTO.builder()
                    .title(" ")
                    .content("Content")
                    .build());
        });
        assertEquals(exception.getMessage(), "Post title can not be empty");
    }

    @Test
    void testCreatePostFailCaseEmptyContentShouldThrowException() {
        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(PostDTO.builder()
                    .title("Title")
                    .content("")
                    .build());
        });
        assertEquals(exception.getMessage(), "Post content can not be empty");
    }

    @Test
    void testCreatePostFailCaseNullContentShouldThrowException() {
        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(PostDTO.builder()
                    .title("Title")
                    .content(null)
                    .build());
        });
        assertEquals(exception.getMessage(), "Post content can not be empty");
    }

    @Test
    void testCreatePostFailCaseBlankContentShouldThrowException() {
        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(PostDTO.builder()
                    .title("Title")
                    .content(" ")
                    .build());
        });
        assertEquals(exception.getMessage(), "Post content can not be empty");
    }

    @Test
    void testUpdatePostSuccess() {
        Post post = Post.builder()
                .title("Entity Title")
                .content("Entity Content")
                .build();

        when(postRepository.findByIDAndVisibility(1)).thenReturn(post);
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post updateDTO = postService.updatePost(1, PostDTO.builder()
                .title("DTO Title")
                .content("DTO Content")
                .build()
        );

        assertNotNull(updateDTO);
        assertEquals("DTO Title", updateDTO.getTitle());
        assertEquals("DTO Content", updateDTO.getContent());
    }

    @Test
    void testUpdatePostFailCaseBlankContentShouldThrowException() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(Post.builder()
                .title("Title")
                .content("Content")
                .build()
        );

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, PostDTO.builder()
                    .title("Title")
                    .content(" ")
                    .build()
            );
        });
        assertEquals(exception.getMessage(), "Post content can not be empty");
    }

    @Test
    void testUpdatePostFailCaseNullContentShouldThrowException() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(Post.builder()
                .title("Title")
                .content("Content")
                .build()
        );

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, PostDTO.builder()
                    .title("Title")
                    .content(null)
                    .build()
            );
        });
        assertEquals(exception.getMessage(), "Post content can not be empty");
    }

    @Test
    void testUpdatePostFailCaseEmptyContentShouldThrowException() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(Post.builder()
                .title("Title")
                .content("Content")
                .build()
        );

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, PostDTO.builder()
                    .title("Title")
                    .content("")
                    .build()
            );
        });
        assertEquals(exception.getMessage(), "Post content can not be empty");
    }

    @Test
    void testUpdatePostFailCaseBlankTitleShouldThrowException() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(Post.builder()
                .title("Title")
                .content("Content")
                .build()
        );

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, PostDTO.builder()
                    .title(" ")
                    .content("Content")
                    .build()
            );
        });
        assertEquals(exception.getMessage(), "Post title can not be empty");
    }

    @Test
    void testUpdatePostFailCaseNullTitleShouldThrowException() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(Post.builder()
                .title("Title")
                .content("Content")
                .build()
        );

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, PostDTO.builder()
                    .title(null)
                    .content("Content")
                    .build()
            );
        });
        assertEquals(exception.getMessage(), "Post title can not be empty");
    }

    @Test
    void testUpdatePostFailCaseEmptyTitleShouldThrowException() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(Post.builder()
                .title("Title")
                .content("Content")
                .build()
        );

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, PostDTO.builder()
                    .title("")
                    .content("Content")
                    .build()
            );
        });
        assertEquals(exception.getMessage(), "Post title can not be empty");
    }


    @Test
    void testDeletePostSuccess() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(Post.builder()
                .title("Title")
                .content("Content")
                .build()
        );
        assertAll(() -> {//To handle void method in service we are using assertAll
            postService.deletePost(1);
        });
    }

    @Test
    void testDeletePostFailShouldThrowException() {
        AppException exception = assertThrows(AppException.class, () -> {
            postService.deletePost(1);
        });

        assertEquals(exception.getMessage(), "Post must be existed in the system");
    }

    @Test
    void testGetPostByIDSuccess() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(Post.builder()
                .title("Title")
                .content("Content")
                .build()
        );
        assertNotNull(postService.getPostByID(1));
    }

    @Test
    void testGetPostByIDFail() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(null);
        AppException exception = assertThrows(AppException.class, () -> {
            postService.getPostByID(1);
        });

        assertEquals(exception.getMessage(), "Post must be existed in the system");
    }

    @Test
    void testGetAllPostSuccess() {
        List<Post> list = new ArrayList<>();
        list.add(Post.builder()
                .title("LIST TITLE 1")
                .content("LIST CONTENT 1")
                .build()
        );
        list.add(Post.builder()
                .title("LIST TITLE 2")
                .content("LIST CONTENT 2")
                .build()
        );

        when(postRepository.findAllVisibility()).thenReturn(list);

        List<Post> posts = postService.getAllPosts();

        assertNotNull(posts);
        assertEquals(2, posts.size());
        assertEquals(list.get(0).getTitle(), posts.get(0).getTitle());
        assertEquals(list.get(0).getContent(), posts.get(0).getContent());
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
        when(postRepository.titleMustBeUnique("Entity title")).thenReturn(true);

        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(PostDTO.builder()
                    .title("Entity title")
                    .content("Entity content")
                    .build());
        });

        assertEquals("Post title must be unique", exception.getMessage());
    }
    ///Jira test #3

    //Jira test 2
    @Test
    void testCreatePostContentContainOffensiveWords() {

        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(PostDTO.builder()
                    .title("Test")
                    .content("F*ck")
                    .build()
            );
        });

        assertEquals("Post content contain offensive word", exception.getMessage());
    }

    @Test
    void testUpdatePost_PostMustExistedInTheSystem() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(null);

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, PostDTO.builder()
                    .title("Test")
                    .content("Test")
                    .build()
            );
        });

        assertEquals("Post must be existed in the system", exception.getMessage());
    }

    @Test
    void testEditPostTitleMustBeUnique() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(Post.builder()
                .title("Old Title")
                .content("Old Content")
                .build()
        );
        when(postRepository.titleMustBeUnique("Old Title")).thenReturn(true);

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, PostDTO.builder()
                    .title("Old Title")
                    .content("Old Content")
                    .build()
            );
        });

        assertEquals("Post title must be unique", exception.getMessage());
    }

    @Test
    void testUpdatePostContentContainOffensiveWords() {
        AppException exception = assertThrows(AppException.class, () -> {
            postService.createPost(PostDTO.builder()
                    .title("Test")
                    .content("F*ck")
                    .build());
        });

        assertEquals("Post content contain offensive word", exception.getMessage());
    }

    @Test
    void testDeletePostIDMustBeExistedInTheSystem() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(null);

        AppException exception = assertThrows(AppException.class, () -> {
            postService.deletePost(1);
        });

        assertEquals("Post must be existed in the system", exception.getMessage());
    }

    @Test
    void testUpdatePost_PostIDMustBeExistedInTheSystem() {
        when(postRepository.findByIDAndVisibility(1)).thenReturn(null);

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, PostDTO.builder()
                    .title("Test")
                    .content("Test")
                    .build()
            );
        });

        assertEquals("Post must be existed in the system", exception.getMessage());
    }

    @Test
    void testUpdatePostTitleMustBeUnique(){
        when(postRepository.findByIDAndVisibility(1)).thenReturn(Post.builder()
                .title("Old Title")
                .content("Old Content")
                .build()
        );
        when(postRepository.titleMustBeUnique("Old Title")).thenReturn(true);

        AppException exception = assertThrows(AppException.class, () -> {
            postService.updatePost(1, PostDTO.builder()
                    .title("Old Title")
                    .content("Old Content")
                    .build()
            );
        });

        assertEquals("Post title must be unique", exception.getMessage());
    }
}