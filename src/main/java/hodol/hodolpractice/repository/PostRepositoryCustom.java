package hodol.hodolpractice.repository;

import hodol.hodolpractice.domain.Post;
import hodol.hodolpractice.request.PageSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PageSearch pageSearch);
}
