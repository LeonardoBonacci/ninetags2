package guru.bonacci.ninetags2;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.SharedWith;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.repos.ShareRepository;
import guru.bonacci.ninetags2.repos.SharedWithRepository;
import guru.bonacci.ninetags2.repos.TopicRepository;
import guru.bonacci.ninetags2.repos.UserRepository;

@EnableAsync
@SpringBootApplication
public class App {


	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository urepo, TopicRepository trepo, ShareRepository srepo, SharedWithRepository swrepo) {
		return args -> {
			trepo.deleteAll();
			
			Topic culture = Topic.builder().name("Culture").build();
			Topic cooking = Topic.builder().name("Cooking").build();
			Topic hobbies = Topic.builder().name("Hobbies").build();
			Topic literature = Topic.builder().name("Literature").build();
			Topic art = Topic.builder().name("Art").build();
			Topic entertainment = Topic.builder().name("Entertainment").build();
			Topic fiction = Topic.builder().name("Fiction").build();
			Topic game = Topic.builder().name("Game").build();
			Topic poetry = Topic.builder().name("Poetry").build();
			Topic sports = Topic.builder().name("Sports").build();
			Topic dance = Topic.builder().name("Dance").build();
			trepo.saveAll(Arrays.asList(culture, cooking, hobbies, literature, art, entertainment, fiction, game, poetry, sports, dance));

			urepo.deleteAll();
			
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
			alpha.addInterests(culture, cooking, hobbies, literature, art, entertainment, fiction, game, poetry, sports);
			beta.addFollows(gamma);
			urepo.saveAll(Arrays.asList(alpha, beta, gamma, delta, epsilon, zeta, eta, theta, iota, kappa, lambda, mu, nu));

			Share sculture = Share.builder().title("On Culture").by(alpha).build();
			Share scooking = Share.builder().title("On Cooking").by(alpha).build();
			Share shobbies = Share.builder().title("On Hobbies").by(alpha).build();
			Share sliterature = Share.builder().title("On Literature").by(alpha).build();
			Share sart = Share.builder().title("On Art").by(alpha).build();
			Share sentertainment = Share.builder().title("On Entertainment").by(alpha).build();
			Share sfiction = Share.builder().title("On Fiction").by(alpha).build();
			Share sgame = Share.builder().title("On Game").by(alpha).build();
			Share spoetry = Share.builder().title("On Poetry").by(alpha).build();
			Share ssports = Share.builder().title("On Sports").by(alpha).build();
			Share sdance = Share.builder().title("On Dance").by(alpha).build();
			srepo.saveAll(Arrays.asList(sculture, scooking, shobbies, sliterature, sart, sentertainment, sfiction, sgame, spoetry, ssports, sdance));

			SharedWith swculture = SharedWith.builder().share(sculture).with(alpha).build();
			SharedWith swcooking = SharedWith.builder().share(scooking).with(alpha).build();
			SharedWith swhobbies = SharedWith.builder().share(shobbies).with(alpha).build();
			SharedWith swliterature = SharedWith.builder().share(sliterature).with(beta).build();

			swrepo.saveAll(Arrays.asList(swculture, swcooking, swhobbies, swliterature));
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
