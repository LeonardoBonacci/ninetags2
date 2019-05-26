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
	CommandLineRunner init(UserRepository repo) {
		return args -> {
			repo.deleteAll();
			
			_User alpha = _User.builder().name("Alpha").build();
			_User beta = _User.builder().name("Beta").build();
			_User gamma = _User.builder().name("Gamma").build();
			_User delta = _User.builder().name("Delta").build();
			_User epsilon = _User.builder().name("Epsilon").build();
			_User zeta = _User.builder().name("Zeta").build();
			_User eta = _User.builder().name("Eta").build();
			_User theta = _User.builder().name("Theta").build();
			_User iota = _User.builder().name("Iota").build();
			_User kappa = _User.builder().name("Kappa").build();
			_User lambda = _User.builder().name("Lambda").build();
			_User mu = _User.builder().name("Mu").build();
			_User nu = _User.builder().name("Nu").build();

			alpha.addFollows(beta, gamma, delta, epsilon, zeta, eta, theta, iota, kappa, lambda, mu);
			beta.addFollows(gamma);
			repo.saveAll(Arrays.asList(alpha, beta, gamma, delta, epsilon, zeta, eta, theta, iota, kappa, lambda, mu, nu));
		};
	}
	
	// Functionalities
	// private bookmarks admin (pocket)
	// private messaging (mailing, like e-mail)
	// public content sharing + content followed
	// content searching
	// recommendations
	// space time search
}
