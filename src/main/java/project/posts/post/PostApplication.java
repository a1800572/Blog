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
			log.info("testdata");
			
			trepository.save(new Tag("cartoon"));
			trepository.save(new Tag("anime"));
			trepository.save(new Tag("horror"));
			trepository.save(new Tag("fantasy"));
			trepository.save(new Tag("action"));

			psrepository.save(new PostStatus("WIP"));
			psrepository.save(new PostStatus("Complete"));

			//testi data
			prepository.save(new Post(null ,"Pekka","päivitys","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Valtteri","päivitys","",psrepository.findByStatus("Complete").get(0)));
			prepository.save(new Post(null ,"Liisa","chapter 1","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Liisa","chapter 2","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Liisa","chapter 3","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Liisa","chapter 4","",psrepository.findByStatus("Complete").get(0)));
			prepository.save(new Post(null ,"Pekka","päivitys 2","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Aino","osa 2","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Pekka","päivitys 3","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Toivo","blog post","moi",psrepository.findByStatus("Complete").get(0)));
			prepository.save(new Post(null ,"Aino","resepti","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Pekka","päivitys 4","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Aino","päivitys 3","",psrepository.findByStatus("WIP").get(0)));
			prepository.save(new Post(null ,"Pekka","päivitys 5","",psrepository.findByStatus("WIP").get(0)));
			log.info("fetch all testdata");
			for (Post post : prepository.findAll()) {
				log.info(post.toString());
			}

		};
	}
}
