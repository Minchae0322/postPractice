package hodol.hodolpractice.controller;

import hodol.hodolpractice.domain.Post;
import hodol.hodolpractice.request.PostCreate;
import hodol.hodolpractice.response.PostResponse;
import hodol.hodolpractice.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/posts")
    public PostResponse post(@RequestBody @Valid PostCreate params) {
        Post post = postService.write(params);
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    @PostMapping("/postsList")
    public List<PostResponse> postList(@RequestBody @Valid List<PostCreate> params) {
        List<Post> posts = postService.writePostList(params);
        return posts.stream()
                .map(post -> PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .build())
                .toList();
    }


    @GetMapping("/postsList")
    public List<PostResponse> getPostList() {

        return postService.getPostList();
    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long postId) {
        return postService.getPost(postId);
    }

}
