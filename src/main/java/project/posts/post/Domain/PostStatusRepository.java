package project.posts.post.Domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostStatusRepository extends CrudRepository<PostStatus, Long> {
    List<PostStatus> findByStatus(String status);
}
