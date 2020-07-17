package project.posts.post.Domain;



import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


@Entity
public class Reply {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long replyid;
	private String text;

	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime creationdatetime;
	
	@ManyToMany(mappedBy = "replies")
    private List<Comment> comments = new ArrayList<>();
	
	
	
	public Reply() {}
	
	public Reply(String text) {
		this.text=text;
	
	}

	public Long getReplyid() {
		return replyid;
	}

	public void setReplyid(Long replyid) {
		this.replyid = replyid;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LocalDateTime getCreationdatetime() {
		return creationdatetime;
	}

	public void setCreationdatetime(LocalDateTime creationdatetime) {
		this.creationdatetime = creationdatetime;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
	
	
}
