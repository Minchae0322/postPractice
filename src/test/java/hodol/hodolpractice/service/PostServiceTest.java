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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.http.HttpResponse;

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

    @DisplayName("글 조회")
    @Test
    void writePostAndQuery() throws Exception {
        PostCreate postCreate = PostCreate.builder()
                .title("글 조회를 위한")
                .content("포스트입니다")
                .build();
        postService.write(postCreate);
        Post post = postService.getPost(1L);
        System.out.println(post.getId());

        assertEquals(post.getId(), 1L);
        assertEquals(post.getContent(), postCreate.getContent());
        assertEquals(post.getTitle(), postCreate.getTitle());

    }
}