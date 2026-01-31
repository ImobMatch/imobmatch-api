package br.com.imobmatch.api;

import br.com.imobmatch.api.configs.dotenv.DotenvInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImobMatchApiApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ImobMatchApiApplication.class);
		app.addInitializers(new DotenvInitializer());
		app.run(args);
	}

}
