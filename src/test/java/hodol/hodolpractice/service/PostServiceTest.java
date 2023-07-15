package hodol.hodolpractice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import hodol.hodolpractice.repository.Post;
import hodol.hodolpractice.repository.PostRepository;
import hodol.hodolpractice.request.PostCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
class PostServiceTest {


    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void before() {
        postRepository.deleteAll();
    }

    @DisplayName("글 작성")
    @Test
    void writeText() throws Exception {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("나는 바보다")
                .content("ㅈㄱㄴ")
                .build();

        postService.write(postCreate);



        Assertions.assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        System.out.println(post.getId());
        assertEquals(postCreate.getTitle(), post.getTitle());
        assertEquals(postCreate.getContent(), post.getContent());
    }

    @DisplayName("글 1개 조회")
    @Test
    void writePostAndQuery() throws Exception {
        PostCreate postCreate = PostCreate.builder()
                .title("글 조회를 위한")
                .content("포스트입니다")
                .build();
        postService.write(postCreate);
        System.out.println(postRepository.findAll().get(0).getId() + " 이거 ");
        Post post = postService.getPost(2L);
        System.out.println(post.getId());

        assertEquals(post.getId(), 2L);
        assertEquals(post.getContent(), postCreate.getContent());
        assertEquals(post.getTitle(), postCreate.getTitle());

    }

    @DisplayName("글 여러개 조회")
    @Test
    void writePostAndQueryAll() throws Exception {
        PostCreate postCreate1 = PostCreate.builder()
                .title("글 조회를 위한")
                .content("포스트입니다1")
                .build();
        PostCreate postCreate2 = PostCreate.builder()
                .title("글 조회를 위한")
                .content("포스트입니다2")
                .build();
        PostCreate postCreate3 = PostCreate.builder()
                .title("글 조회를 위한")
                .content("포스트입니다3")
                .build();
        postService.write(postCreate1);
        postService.write(postCreate2);
        postService.write(postCreate3);
        List<Post> posts = postService.getPostList();

        assertEquals(posts.get(0).getId(), 1L);
        assertEquals(posts.get(2).getContent(), postCreate3.getContent());
        assertEquals(posts.get(1).getTitle(), postCreate2.getTitle());

    }
}