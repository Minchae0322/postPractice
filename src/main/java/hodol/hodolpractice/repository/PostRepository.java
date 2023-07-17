package hodol.hodolpractice.repository;

import hodol.hodolpractice.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom{
}
