package project.posts.post.Domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;


@Entity(name="Post")
@Table(name="post")
public class Post {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String content;

    @Lob
    private byte[] image;
    private String imagename;
    @CreationTimestamp
    private LocalDate date;
    @CreationTimestamp
	private LocalTime time;
    
    
    
    @ManyToMany(cascade = {
	        CascadeType.PERSIST,
	        CascadeType.MERGE
	    })
	    @JoinTable(name = "post_comment",
	        joinColumns = @JoinColumn(name = "id"),
	        inverseJoinColumns = @JoinColumn(name = "commentid")
	    )
    private List<Comment> comments = new ArrayList<>();
    
    
    
    
    
    
    @ManyToMany(cascade = {
	        CascadeType.PERSIST,
	        CascadeType.MERGE
	    })
	    @JoinTable(name = "post_tag",
	        joinColumns = @JoinColumn(name = "id"),
	        inverseJoinColumns = @JoinColumn(name = "tagid")
	    )
	    private List<Tag> tags = new ArrayList<>();
    
    
    
    
    

    public Post() {}

	public Post(byte[] image, String title, String description, String content) {
		super();
		this.title = title;
		this.description = description;
		this.content = content;
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}
	
	

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	
	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getContent() {
		return content;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean hasComment(Comment comment) {
		for (Comment postComment: getComments()) {
			if (postComment.getCommentid() == comment.getCommentid()) {
				return true;
			}
		}
		return false;
}
	public boolean hasTag(Tag tag) {
		for (Tag postTag: getTags()) {
			if (postTag.getTagid() == tag.getTagid()) {
				return true;
			}
		}
		return false;
}

}
