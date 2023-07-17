package hodol.hodolpractice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hodol.hodolpractice.domain.Post;
import hodol.hodolpractice.domain.QPost;
import hodol.hodolpractice.request.PageSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom{


    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Post> getList(PageSearch pageSearch) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(pageSearch.getSize())
                .offset((long) (pageSearch.getSize() - 1) * pageSearch.getPage())
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
