package hodol.hodolpractice.controller;

import hodol.hodolpractice.repository.Post;
import hodol.hodolpractice.request.PostCreate;
import hodol.hodolpractice.service.PostService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;


    @PostMapping("/posts")
    public Post post(@RequestBody @Valid PostCreate params) {
        System.out.println();
        return postService.write(params);
    }

    @GetMapping("/posts/{postId}")
    public Post get(@PathVariable(name = "postId") Long postId) {
        Post post = postService.getPost(postId);
        return post;
    }

}
