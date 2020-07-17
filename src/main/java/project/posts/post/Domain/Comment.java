package project.posts.post.Domain;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity(name="Comment")
@Table(name="comment")
public class Comment {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long commentid;
	private String text;

	@Column(updatable = false)
	@CreationTimestamp
	private LocalDateTime creationdatetime;
	
	@ManyToMany(mappedBy = "comments")
	private List<Post> posts = new ArrayList<>();
	
	
	@ManyToMany(cascade = {
	        CascadeType.PERSIST,
	        CascadeType.MERGE
	    })
	    @JoinTable(name = "comment_reply",
	        joinColumns = @JoinColumn(name = "commentid"),
	        inverseJoinColumns = @JoinColumn(name = "replyid")
	    )
	    private List<Reply> replies = new ArrayList<>();
	
	public Comment() {}
	
	public Comment(String text) {
		super();
		this.text=text;
	}

	public Long getCommentid() {
		return commentid;
	}

	public void setCommentid(Long commentid) {
		this.commentid = commentid;
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

	public List<Reply> getReplies() {
		return replies;
	}

	public void setReplies(List<Reply> replies) {
		this.replies = replies;
	}

	
	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public boolean hasReply(Reply reply) {
		for (Reply commentReply: getReplies()) {
			if (commentReply.getReplyid() == reply.getReplyid()) {
				return true;
			}
		}
		return false;
}

}
