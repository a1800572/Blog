package project.posts.post.Domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;



public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByTitleLike(String title);

    //List<Post> findPostByTitleLikeOrDescriptionLikeOrContentLike(String title, String description, String content);

    Page<Post> findPostByTitleLikeOrDescriptionLikeOrContentLike(String title, String description, String content,Pageable pageable);

    Post getPostById(Long postId);

    Page<Post> findByTags(Tag tag, Pageable pageable);

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByCategories(Category category, Pageable pageable);
}
