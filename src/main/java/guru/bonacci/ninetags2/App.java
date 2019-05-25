package guru.bonacci.ninetags2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class App {


	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	// Functionalities
	// private bookmarks admin (pocket)
	// private messaging (mailing, like e-mail)
	// public content sharing + content followed
	// content searching
	// recommendations
	// space time search
}
