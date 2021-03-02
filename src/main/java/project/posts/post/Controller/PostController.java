package project.posts.post.Controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;


import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import project.posts.post.Domain.*;

import javax.servlet.http.HttpServletRequest;


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

	@Autowired
	private LinkRepository lrepository;

	@Autowired
	private CategoryRepository carepository;

	@Autowired
    private HttpServletRequest request;


	//näyttää kaikki postaukset ekalla sivulla
	@RequestMapping(value="/postlist")
	public String postList(Model model) {
		listByPage(model,1);
		return "postlist";
	}

	//kaikki postaukset paginoituna
	//voidaan poistaa myöhemmin
	//korvaava url /category/view/{id}/page/{pagenumber}
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
	//voidaan poistaa myöhemmin
	//korvaava url /addcategorypost/{id}
	@RequestMapping(value = "/addpost")
	public String addPost(Model model){
		model.addAttribute("post", new Post());
		model.addAttribute("poststatuses", psrepository.findAll());
		return "addpost";
	}

	//voidaan poistaa myöhemmin
	//korvaava url /category/{id}/posts
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

	//poistetaan post olio
	@RequestMapping(value = "/category/{categoryid}/deletepost/{postid}", method = RequestMethod.GET)
	public String deletePost(@PathVariable("categoryid") Long categoryId, @PathVariable("postid") Long postId, Model model) {
		Optional<Post> post = prepository.findById(postId);
		Optional<Category> category = carepository.findById(categoryId);
		category.get().getPosts().remove(post.get());
		prepository.deleteById(postId);
		return "redirect:/category/view/{categoryid}/page/1";
	}

	@RequestMapping(value = "/postlist/view/{id}/page/{pagenumber}", method = RequestMethod.GET)
	public String Viewpost(@PathVariable("id") Long postId, @PathVariable("pagenumber") Integer currentpage, @RequestParam(value = "srt", defaultValue = "") String srt, Viewer viewer, Model model) throws IOException, GeoIp2Exception {
		Optional<Post> post = prepository.findById(postId);
		Sort sort = Sort.by("commentid");
		if (srt.equals("new")){
			sort = Sort.by("creationdatetime").ascending();
		}
		if (srt.equals("old")){
			sort = Sort.by("creationdatetime").descending();
		}
        if (!post.get().hasViewer(viewer)) {
            post.get().getViewers().add(viewer);
			String ip = request.getRemoteAddr();

			File countrydatabase = new File("src"+File.separator+"main"+File.separator+"resources"+File.separator+"GeoLite2-City.mmdb");
			DatabaseReader dbReader = new DatabaseReader.Builder(countrydatabase).build();
			InetAddress ipAddress = InetAddress.getByName(ip);
			CityResponse response = dbReader.city(ipAddress);

			String country = response.getCountry().getName();
			String isocode = response.getCountry().getIsoCode();
			String province = response.getMostSpecificSubdivision().getName();
			String provinceisocode = response.getMostSpecificSubdivision().getIsoCode();
			String city = response.getCity().getName();
			String postalcode = response.getPostal().getCode();

            viewer.setIpadress(ip);
            viewer.setCountryname(country);
            viewer.setIsocode(isocode);
            viewer.setProvince(province);
            viewer.setProvinceisocode(provinceisocode);
            viewer.setCityname(city);
            viewer.setPostalcode(postalcode);
            prepository.save(post.get());
        }

		Page<Comment> comments = crepository.findByPosts(post.get(), PageRequest.of(currentpage-1,5, sort));
		Long totalitems = comments.getTotalElements();
		Integer totalpages = comments.getTotalPages();
		model.addAttribute("post", prepository.getPostById(postId));
		model.addAttribute("comment", new Comment());
		model.addAttribute("reply", new Reply());
		model.addAttribute("currentpage", currentpage);
		model.addAttribute("totalpages", totalpages);
		model.addAttribute("totalitems", totalitems);
		model.addAttribute("comments", comments);
		model.addAttribute("srt", srt);
		model.addAttribute("replies", rrepository.findAll());
		model.addAttribute("categories", carepository.findAll());
		return "viewpost";
	}
	// paginate posts by tag
	@RequestMapping("/tag/{id}/page/{pagenumber}")
	public String posttag(@PathVariable("id") Long tagId, @PathVariable("pagenumber") Integer currentpage, @RequestParam(value = "srt", defaultValue = "") String srt, Model model) {
		Optional<Tag> tag = trepository.findById(tagId);
		//sorting implementointi categoria näkymälle
		//default sorting parametri on id
		Sort sort = Sort.by("id");
		if (srt.equals("title")){
			sort = Sort.by("title").ascending();
		}
		if (srt.equals("creationdatetime")){
			sort = Sort.by("creationdatetime").ascending();
		}
		if (srt.equals("updatedatetime")){
			sort = Sort.by("updatedatetime").descending();
		}
		Page<Post> posts = prepository.findByTags(tag.get(), PageRequest.of(currentpage-1,4, sort));
		Long totalitems = posts.getTotalElements();
		Integer totalpages = posts.getTotalPages();
		String name = tag.get().getName().toUpperCase();
		model.addAttribute("currentpage", currentpage);
		model.addAttribute("totalpages", totalpages);
		model.addAttribute("totalitems", totalitems);
		model.addAttribute("tagid", tagId);
		model.addAttribute("name", name);
		model.addAttribute("srt", srt);
		model.addAttribute("posts", posts);
		model.addAttribute("categories", carepository.findAll());
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
		return "redirect:/postlist/view/{id}/page/1";
	}

	//voidaan poistaa kommentti olio
	@RequestMapping(value="/post/{postid}/deletecomment/{commentid}", method=RequestMethod.GET)
	public String deletecomment(@PathVariable("postid") Long id, @PathVariable("commentid") Long commentId) {
		Optional<Comment> comment = crepository.findById(commentId);
		Optional<Post> post = prepository.findById(id);
		post.get().getComments().remove(comment.get());
		crepository.deleteById(commentId);
		return "redirect:/postlist/view/{postid}/page/1";
	}

	//voidaan poistaa reply olio
	@RequestMapping(value="/{postid}/comment/{commentid}/deletereply/{replyid}", method=RequestMethod.GET)
	public String deletereply(@PathVariable("postid") Long id, @PathVariable("commentid") Long commentId, @PathVariable("replyid") Long replyId) {
		Optional<Comment> comment = crepository.findById(commentId);
		Optional<Reply> reply = rrepository.findById(replyId);
		comment.get().getReplies().remove(reply.get());
		rrepository.deleteById(replyId);
		return "redirect:/postlist/view/{postid}/page/1";
	}


	@RequestMapping(value="/{postid}/comment/{id}/replies", method=RequestMethod.GET)
	public String commentaddreply(@PathVariable("id") Long commentId, @PathVariable("postid") Long id, Model model, Reply reply) {
		Optional<Comment> comment = crepository.findById(commentId);
		if (!comment.get().hasReply(reply)) {
			comment.get().getReplies().add(reply);}
		crepository.save(comment.get());
		model.addAttribute("comment", crepository.findById(commentId));
		model.addAttribute("replies", rrepository.findAll());
		return "redirect:/postlist/view/{postid}/page/1";
	}

	//KESKEN!!!!!!!
	@RequestMapping(value = "/{categoryid}/addPostTag/{id}", method = RequestMethod.GET)
	public String addTagtopost(@PathVariable("id") Long postId, @PathVariable("categoryid") Long categoryId, Model model){
		model.addAttribute("tags", trepository.findAll());
		model.addAttribute("post", prepository.findById(postId).get());
		model.addAttribute("category", carepository.getCategoryByCategoryid(categoryId));
		return "addPostTag";
	}

	//Kesken!!!!
	@RequestMapping(value="/{categoryid}/post/{id}/tags", method=RequestMethod.GET)
	public String postAddTag(@RequestParam(value="action", required=true) String action, @PathVariable("id") Long id, @PathVariable("categoryid") Long categoryId, @RequestParam Long tagId, Model model) {
		Optional<Tag> tag = trepository.findById(tagId);
		Optional<Post> post = prepository.findById(id);

		if (post.isPresent() && action.equalsIgnoreCase("save tag")) {
			if (!post.get().hasTag(tag.get())) {
				post.get().getTags().add(tag.get());
			}
			prepository.save(post.get());
			model.addAttribute("post", prepository.findById(id));
			model.addAttribute("tags", trepository.findAll());
			return "redirect:/{categoryid}/addPostTag/{id}";
		}
		if (post.isPresent() && action.equalsIgnoreCase("remove tag")) {
			post.get().getTags().remove(tag.get());
			prepository.save(post.get());
			//korjaa jos mahdollista
			model.addAttribute("post", prepository.findById(id));
			model.addAttribute("tags", trepository.findAll());
			return "redirect:/{categoryid}/addPostTag/{id}";
		}
		return "redirect:/addPostTag/{id}";

	}

	//luodaan uusi tag olio
	@RequestMapping(value = "/addtag")
	public String addTag(Model model){
		model.addAttribute("tag", new Tag());
		return "addtag";
	}

	//tallennetaan luotu tag olio
	@RequestMapping(value = "/savetag", method = RequestMethod.POST)
	public String saveTag(Tag tag){
		trepository.save(tag);
		return "redirect:/";
	}

	//functional search bar with pagination
	@RequestMapping(value = "/page/{pagenumber}", method = RequestMethod.GET)
	public String search(@PathVariable("pagenumber") Integer currentpage, @RequestParam(defaultValue="") String searchby, @RequestParam(value = "srt", defaultValue = "") String srt, Model model) {
		// sorting implementointi
		//default sorting parametri on id
		Sort sort = Sort.by("id");
		if (srt.equals("title")){
			sort = Sort.by("title").ascending();
		}
		if (srt.equals("creationdatetime")){
			sort = Sort.by("creationdatetime").ascending();
		}
		if (srt.equals("updatedatetime")){
			sort = Sort.by("updatedatetime").descending();
		}
		Page<Post> pageposts = prepository.findPostByTitleLikeOrDescriptionLikeOrContentLike("%"+searchby+"%", "%"+searchby+"%", "%"+searchby+"%",PageRequest.of(currentpage-1,4, sort));
		Long totalitems = pageposts.getTotalElements();
		Integer totalpages = pageposts.getTotalPages();
		model.addAttribute("currentpage", currentpage);
		model.addAttribute("totalpages", totalpages);
		model.addAttribute("totalitems", totalitems);
		model.addAttribute("searchby", searchby);
		model.addAttribute("srt", srt);
		model.addAttribute("posts", pageposts);
		model.addAttribute("categories", carepository.findAll());
		return "searchresults";
	}

	@RequestMapping(value = "/{categoryid}/editpost/{id}")
	public String editpost(@PathVariable("id") Long postId, @PathVariable("categoryid") Long categoryId, Model model){
		model.addAttribute("post", prepository.findById(postId));
		model.addAttribute("category", carepository.getCategoryByCategoryid(categoryId));
		model.addAttribute("poststatuses", psrepository.findAll());
		return "editpost";
	}

	//root
	@RequestMapping(value = "/")
	public String index(Model model){
		model.addAttribute("links", lrepository.findAll());
		model.addAttribute("categories", carepository.findAll());
		return "index";
	}

	//luodaan rating olio
	@RequestMapping(value = "/category/{categoryid}/post/{id}/rating")
	public String addRating(@PathVariable("categoryid") Long categoryId, @PathVariable("id") Long postId, Model model){
		model.addAttribute("post", prepository.getPostById(postId));
		model.addAttribute("category", carepository.getCategoryByCategoryid(categoryId));
		model.addAttribute("rating", new Rating());
		return "addrating";
	}

	//tallennetaan rating olio post oliolle
	@RequestMapping(value="/category/{categoryid}/savepost/{id}/rating", method=RequestMethod.POST)
	public String postaddrating(@PathVariable("id") Long id, Model model, Rating rating) {
		Optional<Post> post = prepository.findById(id);
		if (!post.get().hasRating(rating)) {
			post.get().getRatings().add(rating);}
		prepository.save(post.get());
		model.addAttribute("post", prepository.findById(id));
		model.addAttribute("ratings", rarepository.findAll());
		return "redirect:/category/view/{categoryid}/page/1";
	}

	//luodaan kinkki olio index sivulle
	@RequestMapping(value = "/addlink")
	public String addLink(Model model){
		model.addAttribute("link", new Link());
		return "addlink";
	}

	//tallennetaan linkki olio
	@RequestMapping(value = "/savelink", method=RequestMethod.POST)
	public String savePost(MultipartFile file, Link link, Model model) {
		if (!file.isEmpty()) {
			try {
				String thumbnailimgname = file.getOriginalFilename();
				link.setThumbnailimg(thumbnailimgname);
				String dirLocation = "src\\main\\resources\\static\\thumbnailimages\\";
				if (!new File(dirLocation).exists()) {
					File filea = new File(dirLocation);
					filea.mkdirs();
				}
				byte[] bytes = file.getBytes();
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(dirLocation + new File(thumbnailimgname)));
				bufferedOutputStream.write(bytes);
				bufferedOutputStream.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		lrepository.save(link);
		return "redirect:/";
	}

	@RequestMapping(value = "/addcategory")
	public String addCategory(Model model){
		model.addAttribute("category", new Category());
		return "addcategory";
	}

	@RequestMapping(value = "/savecategory", method=RequestMethod.POST)
	public String saveCategory(Category category) {
		carepository.save(category);
		return "redirect:/";
	}

	@RequestMapping(value = "/category/view/{id}/page/{pagenumber}", method = RequestMethod.GET)
	public String Viewcategory(@PathVariable("id") Long categoryId, @PathVariable("pagenumber") Integer currentpage, @RequestParam(value = "srt", defaultValue = "") String srt, Model model) {
		Optional<Category> category = carepository.findById(categoryId);
		//sorting implementointi
		//default sorting parametri on id
		Sort sort = Sort.by("id");
		if (srt.equals("title")){
			sort = Sort.by("title").ascending();
		}
		if (srt.equals("creationdatetime")){
			sort = Sort.by("creationdatetime").ascending();
		}
		if (srt.equals("updatedatetime")){
			sort = Sort.by("updatedatetime").descending();
		}
		Page<Post> posts = prepository.findByCategories(category.get(), PageRequest.of(currentpage-1,4, sort));
		Long totalitems = posts.getTotalElements();
		Integer totalpages = posts.getTotalPages();
		model.addAttribute("category", carepository.getCategoryByCategoryid(categoryId));
		model.addAttribute("categories", carepository.findAll());
		model.addAttribute("currentpage", currentpage);
		model.addAttribute("totalpages", totalpages);
		model.addAttribute("totalitems", totalitems);
		model.addAttribute("srt", srt);
		model.addAttribute("posts", posts);
		return "categoryview";
	}

	@RequestMapping(value = "/addcategorypost/{id}")
	public String addCategoryPost(@PathVariable("id") Long categoryId, Model model){
		model.addAttribute("category", carepository.getCategoryByCategoryid(categoryId));
		model.addAttribute("post", new Post());
		model.addAttribute("poststatuses", psrepository.findAll());
		return "addcategorypost";
	}

	@RequestMapping(value="/category/{id}/posts", method=RequestMethod.POST)
	public String categoryaddpost(@PathVariable("id") Long categoryId, Model model, Post post, MultipartFile file) {
		Optional<Category> category = carepository.findById(categoryId);
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
		//for adding
		//jos categorialla ei ole postausta
		if (!category.get().hasPost(post)) {
			category.get().getPosts().add(post);
			carepository.save(category.get());}
		//for updating
		//jos categorialla on postaus
		if (category.get().hasPost(post)) {
			prepository.save(post);}

		model.addAttribute("category", carepository.findById(categoryId));
		model.addAttribute("posts", prepository.findAll());
		return "redirect:/category/view/{id}/page/1";
	}


}