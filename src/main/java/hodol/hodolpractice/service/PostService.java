package hodol.hodolpractice.service;

import hodol.hodolpractice.repository.Post;
import hodol.hodolpractice.repository.PostRepository;
import hodol.hodolpractice.request.PostCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public void write(PostCreate post) {
        Post posts = Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        postRepository.save(posts);
    }
}
