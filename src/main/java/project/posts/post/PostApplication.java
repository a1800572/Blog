package project.posts.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import project.posts.post.Domain.Post;
import project.posts.post.Domain.PostRepository;



@SpringBootApplication
public class PostApplication {
	private static final Logger log = LoggerFactory.getLogger(PostApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PostApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner studentDemo(PostRepository prepository) {
		return (args) -> {
			log.info("save a couple of students");
			prepository.save(new Post("John", "Johnson", "john@john.com"));
			prepository.save(new Post("Katy", "Kateson", "kate@kate.com"));	
			
			log.info("fetch all students");
			for (Post post : prepository.findAll()) {
				log.info(post.toString());
			}

		};
	}
}
