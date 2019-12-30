package project.posts.post.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import project.posts.post.Domain.Comment;
import project.posts.post.Domain.CommentRepository;
import project.posts.post.Domain.Post;
import project.posts.post.Domain.PostRepository;
import project.posts.post.Domain.Reply;
import project.posts.post.Domain.ReplyRepository;
import project.posts.post.Domain.Tag;
import project.posts.post.Domain.TagRepository;



@Controller
public class PostController {
	@Autowired
	private PostRepository prepository;
	
	@Autowired
	private CommentRepository crepository;
	
	@Autowired
	private ReplyRepository rrepository;
	
	@Autowired
	private TagRepository trepository;
	
	//näyttää kaikki postaukset
    @RequestMapping(value="/postlist")
    public String postList(Model model) {	
        model.addAttribute("posts", prepository.findAll());
		return "postlist";
    }
  
    //uudelleen ohjaa sivulle jossa voi tehdä postauksen
    @RequestMapping(value = "/addpost")
    public String addPost(Model model){
    	model.addAttribute("post", new Post());
        return "addpost";
    }     
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String savePost(Post post){
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
    
  //KESKEN!!!!!!!
    @RequestMapping(value = "addPostTag/{id}", method = RequestMethod.GET)
    public String addTag(@PathVariable("id") Long postId, Model model){
    		model.addAttribute("tags", trepository.findAll());
    		model.addAttribute("post", prepository.findById(postId).get());
    		return "addPostTag";
    }
    
    //Kesken!!!!
    @RequestMapping(value="/post/{id}/tags", method=RequestMethod.GET)
	public String postAddTag(@RequestParam(value="action", required=true) String action, @PathVariable Long id, @RequestParam Long tagId, Model model) {
    	Optional<Tag> tag = trepository.findById(tagId);
		Optional<Post> post = prepository.findById(id);

		if (post.isPresent() && action.equalsIgnoreCase("save tag")) {
			if (!post.get().hasTag(tag.get())) {
				post.get().getTags().add(tag.get());
			}
			prepository.save(post.get());
			model.addAttribute("post", prepository.findById(id));
			model.addAttribute("tags", trepository.findAll());
			return "redirect:/addPostTag/{id}";
		}
		if (post.isPresent() && action.equalsIgnoreCase("remove tag")) {
				post.get().getTags().remove(tag.get());
			prepository.save(post.get());
			//korjaa jos mahdollista
			model.addAttribute("post", prepository.findById(id));
			model.addAttribute("tags", trepository.findAll());
			return "redirect:/addPostTag/{id}";
		}
		return "redirect:/addPostTag/{id}";
		
	}    
    
    @RequestMapping(value = "/addtag")
    public String addTag(Model model){
    	model.addAttribute("tag", new Tag());
        return "addtag";
    }     
    
    @RequestMapping(value = "/savetag", method = RequestMethod.POST)
    public String saveTag(Tag tag){
        trepository.save(tag);
        return "redirect:postlist";
    }
    
}
