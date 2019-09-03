package project.posts.post.Domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;



public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByLastName(String lastName); 
    
    Post getPostById(Long postId);

}
