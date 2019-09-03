package project.posts.post.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import project.posts.post.Domain.Comment;
import project.posts.post.Domain.CommentRepository;
import project.posts.post.Domain.Post;
import project.posts.post.Domain.PostRepository;
import project.posts.post.Domain.Reply;
import project.posts.post.Domain.ReplyRepository;



@Controller
public class PostController {
	@Autowired
	private PostRepository prepository;
	
	@Autowired
	private CommentRepository crepository;
	
	@Autowired
	private ReplyRepository rrepository;
	
	// http://localhost:8080/studentlist
    @RequestMapping(value="/postlist")
    public String studentList(Model model) {	
        model.addAttribute("posts", prepository.findAll());
		return "postlist";
    }
  
    @RequestMapping(value = "/addpost")
    public String addStudent(Model model){
    	model.addAttribute("post", new Post());
        return "addpost";
    }     
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Post post){
        prepository.save(post);
        return "redirect:postlist";
    }    

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deletePost(@PathVariable("id") Long postId, Model model) {
    	prepository.deleteById(postId);
        return "redirect:../postlist";
    }    
    
    @RequestMapping(value = "/postlist/view/{id}", method = RequestMethod.GET)
	public String Viewpost(@PathVariable("id") Long postId, Model model) {
		model.addAttribute("post", prepository.getPostById(postId));
		// tämän avulla pystytään lisäämään html tiedostoon attribuutti kommentti
		model.addAttribute("comment", new Comment());
		// tämä voidaan poistaa myöhemmin
		model.addAttribute("comments", crepository.findAll());
		
		model.addAttribute("reply", new Reply());
		
		model.addAttribute("replies", rrepository.findAll());
		
		return "viewpost";
    }
    
    // tätä ei toistaiseksi tarvita koska form on view sivulla ja tieto tallennetaan ilman uudelleen ohjausta
    @RequestMapping(value = "/addpostcomment/{id}", method = RequestMethod.GET)
    public String addCourse(@PathVariable("id") Long postId, Model model){
    		model.addAttribute("comment", new Comment());
    		model.addAttribute("comments", crepository.findAll());
    		model.addAttribute("post", prepository.findById(postId).get());
    		return "postcomments";
    }
    
    // tätä ei toistaiseksi tarvita koska form on view sivulla ja tieto tallennetaan ilman uudelleen ohjausta
    @RequestMapping(value = "/addcommentreply/{id}", method = RequestMethod.GET)
    public String addCours(@PathVariable("id") Long commentId, Model model){
    		model.addAttribute("reply", new Reply());
    		model.addAttribute("replies", rrepository.findAll());
    		model.addAttribute("comment", crepository.findById(commentId).get());
    		return "commentreplies";
    }
    
    
    @RequestMapping(value="/post/{id}/comments", method=RequestMethod.GET)
	public String studentsAddCourse(@PathVariable("id") Long id, @PathVariable("id") Long commentId, Model model, Comment comment) {
		Optional<Post> post = prepository.findById(id);
			if (!post.get().hasComment(comment)) {
			post.get().getComments().add(comment);}
			prepository.save(post.get());
			model.addAttribute("post", prepository.findById(id));
			model.addAttribute("comments", crepository.findAll());
			return "redirect:/postlist/view/{id}";
	}
    
    @RequestMapping(value="/comment/{id}/replies", method=RequestMethod.GET)
	public String studentsAddCours(@PathVariable("id") Long commentId, @PathVariable("id") Long replyId, Model model, Reply reply) {
		Optional<Comment> comment = crepository.findById(commentId);
			if (!comment.get().hasReply(reply)) {
			comment.get().getReplies().add(reply);}
			crepository.save(comment.get());
			model.addAttribute("comment", crepository.findById(commentId));
			model.addAttribute("replies", rrepository.findAll());		
			return "redirect:/postlist";
	}
}
