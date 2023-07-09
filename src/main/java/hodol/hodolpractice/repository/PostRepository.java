package hodol.hodolpractice.repository;

import hodol.hodolpractice.request.PostCreate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
