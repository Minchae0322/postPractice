package hodol.hodolpractice.service;

import hodol.hodolpractice.repository.Post;
import hodol.hodolpractice.repository.PostRepository;
import hodol.hodolpractice.request.PostCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public Post write(PostCreate post) {
        Post posts = Post.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        postRepository.save(posts);
        return posts;
    }

    public Post getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다"));
        return post;
    }
}
