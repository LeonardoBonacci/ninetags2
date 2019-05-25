package guru.bonacci.ninetags2;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.repos.UserRepository;

@EnableAsync
@SpringBootApplication
public class App {


	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepo) {
		return args -> {
			userRepo.deleteAll();
			
			_User gamma = _User.builder().name("Gamma").build();
			_User beta = _User.builder().name("Beta").build();
			beta.addFollows(gamma);
			_User alpha = _User.builder().name("Alpha").build();
			alpha.addFollows(beta);
			
			userRepo.saveAll(Arrays.asList(alpha, beta, gamma));		};
	}
	
	// Functionalities
	// private bookmarks admin (pocket)
	// private messaging (mailing, like e-mail)
	// public content sharing + content followed
	// content searching
	// recommendations
	// space time search
}
