package project.posts.post.Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Rating")
@Table(name="rating")
public class Rating {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long ratingid;
    private int star;

    @ManyToMany(mappedBy = "ratings")
    private List<Post> posts = new ArrayList<>();

    public Rating() {}

    public Rating(int star) {
        super();
        this.star=star;
    }

    public Long getRatingid() {
        return ratingid;
    }

    public void setRatingid(Long ratingid) {
        this.ratingid = ratingid;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
