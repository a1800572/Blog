package project.posts.post.Domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;



public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByTitleLike(String title);

    List<Post> findPostByTitleLikeOrDescriptionLike(String title, String description);
    
    Post getPostById(Long postId);

    List<Post> findByTags(Tag tag);
}
