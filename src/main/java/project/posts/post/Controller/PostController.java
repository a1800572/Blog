package project.posts.post.Controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import project.posts.post.Domain.*;


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

	@Autowired
	private PostStatusRepository psrepository;

	@Autowired
	private RatingRepository rarepository;
	
	//näyttää kaikki postaukset ekalla sivulla
    @RequestMapping(value="/postlist")
    public String postList(Model model) {
		listByPage(model,1);
		return "postlist";
    }

    //kaikki postaukset paginoituna
    @RequestMapping(value="/postlist/page/{pagenumber}")
	public String listByPage(Model model, @PathVariable("pagenumber") Integer currentpage){
		Page<Post> pageposts = prepository.findAll(PageRequest.of(currentpage-1,4));
		Long totalitems = pageposts.getTotalElements();
		Integer totalpages = pageposts.getTotalPages();
		model.addAttribute("currentpage", currentpage);
		model.addAttribute("totalpages", totalpages);
		model.addAttribute("totalitems", totalitems);
		model.addAttribute("posts", pageposts);
    	return "postlist";
	}
  
    //uudelleen ohjaa sivulle jossa voi tehdä postauksen
    @RequestMapping(value = "/addpost")
    public String addPost(Model model){
    	model.addAttribute("post", new Post());
    	model.addAttribute("poststatuses", psrepository.findAll());
        return "addpost";
    }     
    
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String savePost(MultipartFile file, Post post, Model model) {
    	if(!file.isEmpty()){
            try {
                String fileName = file.getOriginalFilename();
                post.setImagename(fileName);
                String dirLocation ="src\\main\\resources\\static\\temp\\";
                if(!new File(dirLocation).exists()){
                    File filea = new File(dirLocation);
                    filea.mkdirs();
                }
                byte[] bytes = file.getBytes();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(dirLocation+new File(fileName)));
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    	prepository.save(post);
    	model.addAttribute("post", post);
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
    // paginate posts by tag
    @RequestMapping("/page/{pagenumber}/tag/{id}")
	public String posttag(@PathVariable("id") Long tagId, @PathVariable("pagenumber") Integer currentpage, Model model) {
		Optional<Tag> tag = trepository.findById(tagId);
		Page<Post> posts = prepository.findByTags(tag.get(), PageRequest.of(currentpage-1,4));
		Long totalitems = posts.getTotalElements();
		Integer totalpages = posts.getTotalPages();
		String name = tag.get().getName().toUpperCase();
		model.addAttribute("currentpage", currentpage);
		model.addAttribute("totalpages", totalpages);
		model.addAttribute("totalitems", totalitems);
		model.addAttribute("tagid", tagId);
		model.addAttribute("name", name);
		model.addAttribute("posts", posts);
    	return "tagspostlist";
    }
    
    @RequestMapping(value="/post/{id}/comments", method=RequestMethod.GET)
	public String postaddcomment(@PathVariable("id") Long id, @PathVariable("id") Long commentId, Model model, Comment comment) {
		Optional<Post> post = prepository.findById(id);
			if (!post.get().hasComment(comment)) {
			post.get().getComments().add(comment);}
			prepository.save(post.get());
			model.addAttribute("post", prepository.findById(id));
			model.addAttribute("comments", crepository.findAll());
			return "redirect:/postlist/view/{id}";
	}

	//voidaan poistaa kommentti olio
	@RequestMapping(value="/post/{postid}/deletecomment/{commentid}", method=RequestMethod.GET)
	public String deletecomment(@PathVariable("postid") Long id, @PathVariable("commentid") Long commentId) {
		Optional<Comment> comment = crepository.findById(commentId);
		Optional<Post> post = prepository.findById(id);
			post.get().getComments().remove(comment.get());
			crepository.deleteById(commentId);
			return "redirect:/postlist/view/{postid}";
		}

	//voidaan poistaa reply olio
	@RequestMapping(value="/{postid}/comment/{commentid}/deletereply/{replyid}", method=RequestMethod.GET)
	public String deletereply(@PathVariable("postid") Long id, @PathVariable("commentid") Long commentId, @PathVariable("replyid") Long replyId) {
		Optional<Comment> comment = crepository.findById(commentId);
		Optional<Reply> reply = rrepository.findById(replyId);
		comment.get().getReplies().remove(reply.get());
		rrepository.deleteById(replyId);
		return "redirect:/postlist/view/{postid}";
	}


    @RequestMapping(value="/{postid}/comment/{id}/replies", method=RequestMethod.GET)
	public String commentaddreply(@PathVariable("id") Long commentId, @PathVariable("postid") Long id, Model model, Reply reply) {
		Optional<Comment> comment = crepository.findById(commentId);
			if (!comment.get().hasReply(reply)) {
			comment.get().getReplies().add(reply);}
			crepository.save(comment.get());
			model.addAttribute("comment", crepository.findById(commentId));
			model.addAttribute("replies", rrepository.findAll());		
			return "redirect:/postlist/view/{postid}";
	}
    
  //KESKEN!!!!!!!
    @RequestMapping(value = "addPostTag/{id}", method = RequestMethod.GET)
    public String addTagtopost(@PathVariable("id") Long postId, Model model){
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

	//luodaan uusi tag olio
    @RequestMapping(value = "/addtag")
    public String addTag(Model model){
    	model.addAttribute("tag", new Tag());
        return "addtag";
    }     

    //tallennetaan luotu olio
    @RequestMapping(value = "/savetag", method = RequestMethod.POST)
    public String saveTag(Tag tag){
        trepository.save(tag);
        return "redirect:postlist";
    }

    //functional search bar with pagination
	@RequestMapping(value = "/page/{pagenumber}/search", method = RequestMethod.GET)
	public String search(Model model, @PathVariable("pagenumber") Integer currentpage, @RequestParam(defaultValue="") String searchby) {
		//model.addAttribute("posts", prepository.findByTitleLike("%"+searchby+"%"));
		Page<Post> pageposts = prepository.findPostByTitleLikeOrDescriptionLikeOrContentLike("%"+searchby+"%", "%"+searchby+"%", "%"+searchby+"%",PageRequest.of(currentpage-1,4));

		Long totalitems = pageposts.getTotalElements();
		Integer totalpages = pageposts.getTotalPages();

		model.addAttribute("currentpage", currentpage);
		model.addAttribute("totalpages", totalpages);
		model.addAttribute("totalitems", totalitems);
		model.addAttribute("searchby", searchby);
		model.addAttribute("posts", pageposts);
		return "searchresults";
	}

	@RequestMapping(value = "/editpost/{id}")
	public String editpost(@PathVariable("id") Long postId, Model model){
		model.addAttribute("post", prepository.findById(postId));
		model.addAttribute("poststatuses", psrepository.findAll());
    	return "editpost";
	}

	//root
	@RequestMapping(value = "/")
	public String index(){
    	return "index";
	}

	//luodaan rating olio
	@RequestMapping(value = "/addPostrating/{id}")
	public String addRating(@PathVariable("id") Long postId, Model model){
		model.addAttribute("post", prepository.getPostById(postId));
		model.addAttribute("rating", new Rating());
		return "addrating";
	}

	//tallennetaan rating olio post oliolle
	@RequestMapping(value="/post/{id}/ratings", method=RequestMethod.GET)
	public String postaddrating(@PathVariable("id") Long id, Model model, Rating rating) {
		Optional<Post> post = prepository.findById(id);
		if (!post.get().hasRating(rating)) {
			post.get().getRatings().add(rating);}
		prepository.save(post.get());
		model.addAttribute("post", prepository.findById(id));
		model.addAttribute("ratings", rarepository.findAll());
		return "redirect:/postlist";
	}

}
