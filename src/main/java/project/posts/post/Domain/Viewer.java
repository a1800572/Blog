package project.posts.post.Domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name="Viewer")
@Table(name="viewer")
public class Viewer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long viewerid;
    private String ipadress;
    private String location;

    @CreationTimestamp
    private LocalDateTime viewdate;

    @ManyToMany(mappedBy = "viewers")
    private List<Post> posts = new ArrayList<>();

    public Viewer() {}

    public Viewer(String location) {
        super();
        this.location=location;
    }

    public Long getViewerid() {
        return viewerid;
    }

    public void setViewerid(Long viewerid) {
        this.viewerid = viewerid;
    }

    public String getIpadress() {
        return ipadress;
    }

    public void setIpadress(String ipadress) {
        this.ipadress = ipadress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getViewdate() {
        return viewdate;
    }

    public void setViewdate(LocalDateTime viewdate) {
        this.viewdate = viewdate;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
