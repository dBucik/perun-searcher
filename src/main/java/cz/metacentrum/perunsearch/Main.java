package cz.metacentrum.perunsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath:*applicationContext.xml"})
@SpringBootApplication
public class Main extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

}
