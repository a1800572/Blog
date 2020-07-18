package project.posts.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import project.posts.post.Domain.*;


@SpringBootApplication
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
	public CommandLineRunner studentDemo(PostRepository prepository, TagRepository trepository, PostStatusRepository psrepository, RatingRepository rarepository) {
		return (args) -> {
			log.info("testdata");

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
