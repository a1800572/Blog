package project.posts.post.Domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name="Comment")
@Table(name="comment")
public class Comment {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long commentid;
	private String text;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	private Post post;
	
	
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

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
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
