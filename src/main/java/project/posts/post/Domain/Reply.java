package project.posts.post.Domain;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;



@Entity
public class Reply {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long replyid;
	private String text;
	
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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
	
	
}
