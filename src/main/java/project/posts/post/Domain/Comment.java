package project.posts.post.Domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity(name="Comment")
@Table(name="comment")
public class Comment {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long commentid;
	private String text;
	
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
