package hodol.hodolpractice.service;

import hodol.hodolpractice.domain.Post;
import hodol.hodolpractice.domain.PostEdit;
import hodol.hodolpractice.repository.PostRepository;
import hodol.hodolpractice.request.PostCreate;
import hodol.hodolpractice.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<Post> writePostList(List<PostCreate> postCreates) {
        List<Post> postList = postCreates.stream()
                .map(postCreate -> Post.builder()
                        .title(postCreate.getTitle())
                        .content(postCreate.getTitle())
                        .build())
                        .toList();
        postRepository.saveAll(postList);
        return postList;
    }

    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다"));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public Long getFirstPostId() {
        return postRepository.findAll().get(0).getId();
    }

    public List<PostResponse> getPostList() {
        return postRepository.findAll()
                .stream()
                .map(post -> PostResponse.builder()
                            .id(post.getId())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .build()
                ).collect(Collectors.toList());
    }

    public PostResponse edit(Long postId, PostEdit postEdit) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청입니다."));

        post.edit(postEdit.getTitle(), postEdit.getContent());

        postRepository.save(post);

        return PostResponse.builder().id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public PostResponse delete(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("글이 존재하지 않습니다"));

        PostResponse postResponse = PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        postRepository.delete(post);
        return postResponse;
    }

}
