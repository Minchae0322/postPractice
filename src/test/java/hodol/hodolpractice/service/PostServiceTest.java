package hodol.hodolpractice.service;

import hodol.hodolpractice.domain.Post;
import hodol.hodolpractice.domain.PostEdit;
import hodol.hodolpractice.repository.PostRepository;
import hodol.hodolpractice.request.PageSearch;
import hodol.hodolpractice.request.PostCreate;
import hodol.hodolpractice.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

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
        Post post = Post.builder()
                .title("글 조회를 위한")
                .content("포스트입니다")
                .build();
        postRepository.save(post);


        PostResponse post1 = postService.getPost(post.getId());
        assertEquals(post.getId(), post1.getId());
        assertEquals(post.getContent(), post1.getContent());
        assertEquals(post.getTitle(), post1.getTitle());

    }

    @DisplayName("글 여러개 조회")
    @Test
    void writePostAndQueryAll2() throws Exception {
        PostCreate postCreate1 = PostCreate.builder()
                .title("글 조회를 위한 포스트입니다 글자수는 10글자입니다")
                .content("포스트입니다1")
                .build();
        PostCreate postCreate2 = PostCreate.builder()
                .title("글 조회를 위한 포스트입니다 제목 글자수는 10글자입니다")
                .content("포스트입니다2")
                .build();
        PostCreate postCreate3 = PostCreate.builder()
                .title("글 조회를 위한 포스트입니다 제목 글자수는 10글자입니다")
                .content("포스트입니다3")
                .build();
        postService.write(postCreate1);
        postService.write(postCreate2);
        postService.write(postCreate3);
        List<PostResponse> posts = postService.getPostList();

        assertEquals(posts.get(0).getId(), 1L);
        assertEquals(posts.get(2).getContent(), postCreate3.getContent());
        assertEquals(posts.get(1).getTitle(), "글 조회를 위한 포");

    }

    @DisplayName("글 여러개 조회")
    @Test
    void writePostAndQueryAll11() throws Exception {
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("제목" + i)
                        .content("내용" + i).build())
                .toList();

        postRepository.saveAll(requestPosts);
        List<Post> posts = postRepository.getList(new PageSearch(1, 10));

        assertEquals(posts.size(), 10);
        assertEquals(posts.get(0).getTitle() , "제목10");


    }

    @DisplayName("글 변경")
    @Test
    void editPost() throws Exception {
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("제목" + i)
                        .content("내용" + i).build())
                .toList();

        postRepository.saveAll(requestPosts);
        PostEdit postEdit = PostEdit.builder()
                .title("메롱")
                .content("바보")
                .build();
        postService.edit(requestPosts.get(0).getId(), postEdit);

        Post post = postRepository.findById(requestPosts.get(0).getId())
                .orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다"));


        assertEquals(post.getTitle(), "메롱");



    }

    @DisplayName("글 삭제")
    @Test
    void deletePost() throws Exception {
        Post post = Post.builder()
                .title("글 조회를 위한 포스트입니다 글자수는 10글자입니다")
                .content("포스트입니다1")
                .build();

        postRepository.save(post);

        postService.delete(post.getId());

        assertEquals(postRepository.count(), 0);


    }
}