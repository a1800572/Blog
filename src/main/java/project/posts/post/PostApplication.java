package project.posts.post;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import project.posts.post.Domain.*;


@SpringBootApplication
@EnableEncryptableProperties
public class PostApplication {
	private static final Logger log = LoggerFactory.getLogger(PostApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PostApplication.class, args);
	}
	@Bean
	 public CommonsMultipartResolver multipartResolver() {
	  CommonsMultipartResolver resolver = new CommonsMultipartResolver();
	  resolver.setDefaultEncoding("utf-8");
	  resolver.setResolveLazily(false);
	  resolver.setMaxUploadSize(200000);
	  return resolver;
	 }
	
	@Bean
	public CommandLineRunner blogDemo(PostRepository prepository, TagRepository trepository, PostStatusRepository psrepository, RatingRepository rarepository, CommentRepository crepository, LinkRepository lrepository) {
		return (args) -> {
			log.info("testdata");

			//testi linkit
			lrepository.save(new Link("Iltasanomat","https://www.is.fi/","ISlogo.png"));
			lrepository.save(new Link("Youtube","https://www.youtube.com/","YTB.png"));

			//testi tagit
			Tag tag1 = trepository.save(new Tag("cartoon"));
			Tag tag2 = trepository.save(new Tag("anime"));
			Tag tag3 = trepository.save(new Tag("horror"));
			Tag tag4 = trepository.save(new Tag("fantasy"));
			Tag tag5 = trepository.save(new Tag("action"));

			//testi statukset
			psrepository.save(new PostStatus("WIP"));
			psrepository.save(new PostStatus("Complete"));

			//testi ratingit
			Rating rating1 = rarepository.save(new Rating(2));
			Rating rating2 = rarepository.save(new Rating(5));
			Rating rating3 = rarepository.save(new Rating(3));
			Rating rating4 = rarepository.save(new Rating(4));
			Rating rating5 = rarepository.save(new Rating(1));

			//testi postaukset
			Post post1 = prepository.save(new Post(null ,"Pekka","päivitys","",psrepository.findByStatus("WIP").get(0)));
			Post post2 = prepository.save(new Post(null ,"Valtteri","päivitys","",psrepository.findByStatus("Complete").get(0)));
			Post post3 = prepository.save(new Post(null ,"Liisa","chapter 1","",psrepository.findByStatus("WIP").get(0)));
			Post post4 = prepository.save(new Post(null ,"Liisa","chapter 2","",psrepository.findByStatus("WIP").get(0)));
			Post post5 = prepository.save(new Post(null ,"Liisa","chapter 3","",psrepository.findByStatus("WIP").get(0)));
			Post post6 = prepository.save(new Post(null ,"Liisa","chapter 4","",psrepository.findByStatus("Complete").get(0)));
			Post post7 = prepository.save(new Post(null ,"Pekka","päivitys 2","",psrepository.findByStatus("WIP").get(0)));
			Post post8 = prepository.save(new Post(null ,"Aino","osa 2","",psrepository.findByStatus("WIP").get(0)));
			Post post9 = prepository.save(new Post(null ,"Pekka","päivitys 3","",psrepository.findByStatus("WIP").get(0)));
			Post post10 = prepository.save(new Post(null ,"Toivo","blog post","moi",psrepository.findByStatus("Complete").get(0)));
			Post post11 = prepository.save(new Post(null ,"Aino","resepti","",psrepository.findByStatus("WIP").get(0)));

			prepository.save(new Post(null ,"Pekka","päivitys 4","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Aino","päivitys 3","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Pekka","päivitys 5","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Laura","päivä 1","",psrepository.findByStatus("Complete").get(0)));
			prepository.save(new Post(null ,"Satu","viikko 7","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Pinja","koulu","",psrepository.findByStatus("Complete").get(0)));
			prepository.save(new Post(null ,"vilinä","ruokalista","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"jukka","muistilappu","",psrepository.findByStatus("Complete").get(0)));
			prepository.save(new Post(null ,"juho","työhakemus","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Pekka","päivitys 5","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Aino","päivitys 4","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Pekka","päivitys 6","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Pekka","päivitys 6","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Aino","päivitys 5","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Pekka","päivitys 7","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Pekka","päivitys 7","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Aino","päivitys 6","",psrepository.findByStatus("Complete").get(0)));
			prepository.save(new Post(null ,"Pekka","päivitys 8","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Pekka","päivitys 8","",psrepository.findByStatus("Complete").get(0)));
			prepository.save(new Post(null ,"Aino","päivitys 7","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Pekka","päivitys 9","",psrepository.findByStatus("Complete").get(0)));

			//testi kommentit
			Comment comment1 = crepository.save(new Comment("Comment 1"));
			Comment comment2 = crepository.save(new Comment("Comment 2"));
			Comment comment3 = crepository.save(new Comment("Comment 3"));
			Comment comment4 = crepository.save(new Comment("Comment 4"));
			Comment comment5 = crepository.save(new Comment("Comment 5"));
			Comment comment6 = crepository.save(new Comment("Comment 6"));
			Comment comment7 = crepository.save(new Comment("Comment 7"));
			Comment comment8 = crepository.save(new Comment("Comment 8"));
			Comment comment9 = crepository.save(new Comment("Comment 9"));
			Comment comment10 = crepository.save(new Comment("Comment 10"));
			Comment comment11 = crepository.save(new Comment("Comment 11"));
			Comment comment12 = crepository.save(new Comment("Comment 12"));
			Comment comment13 = crepository.save(new Comment("Comment 13"));
			Comment comment14 = crepository.save(new Comment("Comment 14"));
			Comment comment15 = crepository.save(new Comment("Comment 15"));
			Comment comment16 = crepository.save(new Comment("Comment 16"));
			Comment comment17 = crepository.save(new Comment("Comment 17"));
			Comment comment18 = crepository.save(new Comment("Comment 18"));
			Comment comment19 = crepository.save(new Comment("Comment 19"));
			Comment comment20 = crepository.save(new Comment("Comment 20"));
			Comment comment21 = crepository.save(new Comment("Comment 21"));
			Comment comment22 = crepository.save(new Comment("Comment 22"));
			Comment comment23 = crepository.save(new Comment("Comment 23"));
			Comment comment24 = crepository.save(new Comment("Comment 24"));
			Comment comment25 = crepository.save(new Comment("Comment 25"));
			Comment comment26 = crepository.save(new Comment("Comment 26"));
			Comment comment27 = crepository.save(new Comment("Comment 27"));
			Comment comment28 = crepository.save(new Comment("Comment 28"));
			Comment comment29 = crepository.save(new Comment("Comment 29"));
			Comment comment30 = crepository.save(new Comment("Comment 30"));
			Comment comment31 = crepository.save(new Comment("Comment 31"));
			Comment comment32 = crepository.save(new Comment("Comment 32"));
			Comment comment33 = crepository.save(new Comment("Comment 33"));
			Comment comment34 = crepository.save(new Comment("Comment 34"));
			Comment comment35 = crepository.save(new Comment("Comment 35"));

			//post comments testi data
			post1.getComments().add(comment1);
			comment1.getPosts().add(post1);

			post1.getComments().add(comment2);
			comment2.getPosts().add(post1);

			post1.getComments().add(comment3);
			comment3.getPosts().add(post1);

			post1.getComments().add(comment4);
			comment4.getPosts().add(post1);

			post1.getComments().add(comment5);
			comment5.getPosts().add(post1);

			post1.getComments().add(comment6);
			comment6.getPosts().add(post1);

			post1.getComments().add(comment7);
			comment7.getPosts().add(post1);

			post1.getComments().add(comment8);
			comment8.getPosts().add(post1);

			post1.getComments().add(comment9);
			comment9.getPosts().add(post1);

			post1.getComments().add(comment10);
			comment10.getPosts().add(post1);

			post1.getComments().add(comment11);
			comment11.getPosts().add(post1);

			post1.getComments().add(comment12);
			comment12.getPosts().add(post1);

			post1.getComments().add(comment13);
			comment13.getPosts().add(post1);

			post1.getComments().add(comment14);
			comment14.getPosts().add(post1);

			post1.getComments().add(comment15);
			comment15.getPosts().add(post1);

			post1.getComments().add(comment16);
			comment16.getPosts().add(post1);

			post1.getComments().add(comment17);
			comment17.getPosts().add(post1);

			post1.getComments().add(comment18);
			comment18.getPosts().add(post1);

			post1.getComments().add(comment19);
			comment19.getPosts().add(post1);

			post1.getComments().add(comment20);
			comment20.getPosts().add(post1);

			post1.getComments().add(comment21);
			comment21.getPosts().add(post1);

			post1.getComments().add(comment22);
			comment22.getPosts().add(post1);

			post1.getComments().add(comment23);
			comment23.getPosts().add(post1);

			post1.getComments().add(comment24);
			comment24.getPosts().add(post1);

			post1.getComments().add(comment25);
			comment25.getPosts().add(post1);

			post1.getComments().add(comment26);
			comment26.getPosts().add(post1);

			post1.getComments().add(comment27);
			comment27.getPosts().add(post1);

			post1.getComments().add(comment28);
			comment28.getPosts().add(post1);

			post1.getComments().add(comment29);
			comment29.getPosts().add(post1);

			post1.getComments().add(comment30);
			comment30.getPosts().add(post1);

			post1.getComments().add(comment31);
			comment31.getPosts().add(post1);

			post1.getComments().add(comment32);
			comment32.getPosts().add(post1);

			post1.getComments().add(comment33);
			comment33.getPosts().add(post1);

			post1.getComments().add(comment34);
			comment34.getPosts().add(post1);

			post1.getComments().add(comment35);
			comment35.getPosts().add(post1);


			//post ratings testi data
			post1.getRatings().add(rating1);
			rating1.getPosts().add(post1);

			post1.getRatings().add(rating3);
			rating3.getPosts().add(post1);

			post2.getRatings().add(rating2);
			rating2.getPosts().add(post2);

			post2.getRatings().add(rating5);
			rating5.getPosts().add(post2);

			post5.getRatings().add(rating1);
			rating1.getPosts().add(post5);

			post7.getRatings().add(rating2);
			rating2.getPosts().add(post7);

			post4.getRatings().add(rating1);
			rating1.getPosts().add(post4);

			post3.getRatings().add(rating3);
			rating3.getPosts().add(post3);

			//post tags testidata
			post1.getTags().add(tag1);
			tag1.getPosts().add(post1);

			post1.getTags().add(tag5);
			tag5.getPosts().add(post1);

			post1.getTags().add(tag4);
			tag4.getPosts().add(post1);

			post1.getTags().add(tag2);
			tag2.getPosts().add(post1);

			post2.getTags().add(tag5);
			tag5.getPosts().add(post2);

			post2.getTags().add(tag3);
			tag3.getPosts().add(post2);

			post3.getTags().add(tag2);
			tag2.getPosts().add(post3);

			post3.getTags().add(tag4);
			tag4.getPosts().add(post3);

			post3.getTags().add(tag1);
			tag1.getPosts().add(post3);

			post4.getTags().add(tag2);
			tag2.getPosts().add(post4);

			post4.getTags().add(tag1);
			tag1.getPosts().add(post4);

			post4.getTags().add(tag4);
			tag4.getPosts().add(post4);

			post7.getTags().add(tag3);
			tag3.getPosts().add(post7);

			post7.getTags().add(tag5);
			tag5.getPosts().add(post7);

			post5.getTags().add(tag3);
			tag3.getPosts().add(post5);

			post5.getTags().add(tag1);
			tag1.getPosts().add(post5);

			post5.getTags().add(tag3);
			tag3.getPosts().add(post5);

			post6.getTags().add(tag1);
			tag1.getPosts().add(post6);

			post8.getTags().add(tag1);
			tag1.getPosts().add(post8);

			post8.getTags().add(tag3);
			tag3.getPosts().add(post8);

			post9.getTags().add(tag4);
			tag4.getPosts().add(post9);

			post9.getTags().add(tag1);
			tag1.getPosts().add(post9);

			post9.getTags().add(tag5);
			tag5.getPosts().add(post9);

			post11.getTags().add(tag4);
			tag4.getPosts().add(post11);

			post10.getTags().add(tag4);
			tag4.getPosts().add(post10);

			post10.getTags().add(tag1);
			tag1.getPosts().add(post10);


			prepository.save(post1);
			prepository.save(post2);
			prepository.save(post5);
			prepository.save(post7);
			prepository.save(post4);
			prepository.save(post3);
			prepository.save(post6);
			prepository.save(post8);
			prepository.save(post9);
			prepository.save(post11);
			prepository.save(post10);

			log.info("fetch all testdata");
			for (Post post : prepository.findAll()) {
				log.info(post.toString());
			}

		};
	}
}
