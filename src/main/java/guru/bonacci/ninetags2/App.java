package guru.bonacci.ninetags2;

import static java.util.Arrays.*;

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
import lombok.val;

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
			
			val culture = Topic.builder().name("Culture").build();
			val cooking = Topic.builder().name("Cooking").build();
			val hobbies = Topic.builder().name("Hobbies").build();
			val literature = Topic.builder().name("Literature").build();
			val art = Topic.builder().name("Art").build();
			val entertainment = Topic.builder().name("Entertainment").build();
			val fiction = Topic.builder().name("Fiction").build();
			val game = Topic.builder().name("Game").build();
			val poetry = Topic.builder().name("Poetry").build();
			val sports = Topic.builder().name("Sports").build();
			val dance = Topic.builder().name("Dance").build();
			trepo.saveAll(asList(culture, cooking, hobbies, literature, art, entertainment, fiction, game, poetry, sports, dance));

			urepo.deleteAll();
			
			val alpha = _User.builder().name("Alpha").build();
			val beta = _User.builder().name("Beta").build();
			val gamma = _User.builder().name("Gamma").build();
			val delta = _User.builder().name("Delta").build();
			val epsilon = _User.builder().name("Epsilon").build();
			val zeta = _User.builder().name("Zeta").build();
			val eta = _User.builder().name("Eta").build();
			val theta = _User.builder().name("Theta").build();
			val iota = _User.builder().name("Iota").build();
			val kappa = _User.builder().name("Kappa").build();
			val lambda = _User.builder().name("Lambda").build();
			val mu = _User.builder().name("Mu").build();
			val nu = _User.builder().name("Nu").build();

			alpha.addFollows(beta, gamma, delta, epsilon, zeta, eta, theta, iota, kappa, lambda, mu);
			alpha.addInterests(culture, cooking, hobbies, literature, art, entertainment, fiction, game, poetry, sports);
			beta.addFollows(gamma);
			urepo.saveAll(asList(alpha, beta, gamma, delta, epsilon, zeta, eta, theta, iota, kappa, lambda, mu, nu));

			val sculture = Share.builder().title("On Culture").by(alpha).build();
			val scooking = Share.builder().title("On Cooking").by(alpha).build();
			val shobbies = Share.builder().title("On Hobbies").by(alpha).build();
			val sliterature = Share.builder().title("On Literature").by(alpha).build();
			val sart = Share.builder().title("On Art").by(alpha).build();
			val sentertainment = Share.builder().title("On Entertainment").by(alpha).build();
			val sfiction = Share.builder().title("On Fiction").by(alpha).build();
			val sgame = Share.builder().title("On Game").by(alpha).build();
			val spoetry = Share.builder().title("On Poetry").by(alpha).build();
			val ssports = Share.builder().title("On Sports").by(alpha).build();
			val sdance = Share.builder().title("On Dance").by(alpha).build();
			srepo.saveAll(asList(sculture, scooking, shobbies, sliterature, sart, sentertainment, sfiction, sgame, spoetry, ssports, sdance));

			val swculture = SharedWith.builder().share(sculture).with(alpha).build();
			val swcooking = SharedWith.builder().share(scooking).with(alpha).build();
			val swhobbies = SharedWith.builder().share(shobbies).with(alpha).build();
			val swliterature = SharedWith.builder().share(sliterature).with(beta).build();

			swrepo.saveAll(asList(swculture, swcooking, swhobbies, swliterature));
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
