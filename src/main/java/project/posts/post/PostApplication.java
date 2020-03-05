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
	public CommandLineRunner studentDemo(PostRepository prepository, TagRepository trepository, PostStatusRepository psrepository) {
		return (args) -> {
			log.info("save a couple of students");
			
			trepository.save(new Tag("cartoon"));
			trepository.save(new Tag("anime"));

			psrepository.save(new PostStatus("WIP"));
			psrepository.save(new PostStatus("Complete"));
			log.info("fetch all students");
			for (Post post : prepository.findAll()) {
				log.info(post.toString());
			}

		};
	}
}
