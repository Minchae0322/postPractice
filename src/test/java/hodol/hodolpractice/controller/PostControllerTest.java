package hodol.hodolpractice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hodol.hodolpractice.repository.PostRepository;
import hodol.hodolpractice.request.PostCreate;
import hodol.hodolpractice.response.PostResponse;
import hodol.hodolpractice.service.PostService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {


    @Autowired
    private PostService postService;
    //@Autowired
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("controllerAdvice의 오류처리를 테스트한다")
    void test() throws Exception {
        PostCreate postCreate = PostCreate.builder()
                .title(null)
                .content("내용입니다")
                .build();


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(postCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다"))
                .andDo(print());

       // Assertions.assertEquals(1L, postRepository.count());
    }

    @DisplayName("게시글 조회")
    @Test
    void testPostQuery() throws Exception{
        //save
        PostCreate postCreate = PostCreate.builder()
                .title("글 조회를 위한")
                .content("포스트입니다")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(postCreate);
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
        //get
        PostResponse post = postService.getPost(1L);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @DisplayName("제목을 10글자로 제한")
    @Test
    void testPostResponse() throws Exception{
        //save
        PostCreate postCreate = PostCreate.builder()
                .title("123456789012345678")
                .content("포스트입니다")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(postCreate);
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());



        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}",  postService.getFirstPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("1234567890"))
                .andDo(print());

    }

    @DisplayName("글 목록을 전부 가져오기")
    @Test
    void testPostQueryList() throws Exception{
        //save
        PostCreate postCreate = PostCreate.builder()
                .title("123456789012345678")
                .content("포스트입니다")
                .build();
        PostCreate postCreate2 = PostCreate.builder()
                .title("123456789012345678")
                .content("포스트입니다")
                .build();
        PostCreate postCreate3 = PostCreate.builder()
                .title("123456789012345678")
                .content("포스트입니다")
                .build();
        List<PostCreate> list = new ArrayList<>();
        list.add(postCreate);
        list.add(postCreate2);
        list.add(postCreate3);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(list);

        mockMvc.perform(MockMvcRequestBuilders.post("/postsList")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.get("/postsList")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", is(3)))
                .andDo(print());


    }
    @DisplayName("글 수정")
    @Test
    void eidtPost() throws Exception{
        //save
        PostCreate postCreate = PostCreate.builder()
                .title("123456789012345678")
                .content("포스트입니다")
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(postCreate);
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());



        mockMvc.perform(MockMvcRequestBuilders.get("/posts/{postId}",  postService.getFirstPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("1234567890"))
                .andDo(print());

    }
}