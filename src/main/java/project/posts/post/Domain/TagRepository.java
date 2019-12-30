package project.posts.post.Domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long>{
	
	List<Tag> findByName(String name);

}
