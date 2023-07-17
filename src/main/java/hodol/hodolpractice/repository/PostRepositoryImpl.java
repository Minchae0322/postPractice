package hodol.hodolpractice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hodol.hodolpractice.domain.Post;
import hodol.hodolpractice.domain.QPost;
import hodol.hodolpractice.request.PageSearch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PageSearch pageSearch) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(pageSearch.getSize())
                .offset((long) (pageSearch.getPage() - 1) * pageSearch.getSize())
                .orderBy(QPost.post.id.desc())
                .fetch();
    }


}
