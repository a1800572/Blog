package project.posts.post.Domain;

import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category getCategoryByCategoryid(Long categoryId);
}
