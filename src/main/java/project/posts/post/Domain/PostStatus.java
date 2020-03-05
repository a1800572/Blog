package project.posts.post.Domain;

import javax.persistence.*;
import java.util.List;

@Entity(name="PostStatus")
@Table(name="poststatus")
public class PostStatus {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long poststatusid;
    private String status;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="poststatus")
    private List<Post> posts;

    public PostStatus(){}

    public PostStatus(String status){
        super();
        this.status = status;
    }

    public Long getPoststatusid() {
        return poststatusid;
    }

    public String getStatus() {
        return status;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPoststatusid(Long poststatusid) {
        this.poststatusid = poststatusid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
