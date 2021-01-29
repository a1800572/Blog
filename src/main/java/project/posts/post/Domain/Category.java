package project.posts.post.Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Category")
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long categoryid;
    private String categoryname;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "category_post",
            joinColumns = @JoinColumn(name = "categoryid"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Post> posts = new ArrayList<>();

    public Category(){}

    public Category(String categoryname){
        super();
        this.categoryname=categoryname;
    }

    public Long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public boolean hasPost(Post post) {
        for (Post categoryPost: getPosts()) {
            if (categoryPost.getId() == post.getId()) {
                return true;
            }
        }
        return false;
    }
}
