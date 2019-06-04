package guru.bonacci.ninetags2;

import static java.util.Arrays.asList;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import guru.bonacci.ninetags2.domain.Likes;
import guru.bonacci.ninetags2.domain.Share;
import guru.bonacci.ninetags2.domain.SharedWith;
import guru.bonacci.ninetags2.domain.Topic;
import guru.bonacci.ninetags2.domain._User;
import guru.bonacci.ninetags2.repos.LikesRepository;
import guru.bonacci.ninetags2.repos.ShareRepository;
import guru.bonacci.ninetags2.repos.SharedWithRepository;
import guru.bonacci.ninetags2.repos.TopicRepository;
import guru.bonacci.ninetags2.repos.UserRepository;
import lombok.val;

@EnableAsync
@SpringBootApplication
public class App {

	// Functionalities:
	// > private bookmarks admin (pocket)
	// > private messaging (mailing, like e-mail)
	// > public content sharing + content followed
	// > content searching
	// > recommendations
	// > space time search


	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepo, 
							TopicRepository topicRepo, 
							ShareRepository shareRepo, 
							SharedWithRepository sharedWithRepo,
							LikesRepository likesRepo) {
		return args -> {
			userRepo.deleteAll();
			topicRepo.deleteAll();
			shareRepo.deleteAll();
			sharedWithRepo.deleteAll();
			likesRepo.deleteAll();
			
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
			topicRepo.saveAll(asList(culture, cooking, hobbies, literature, art, entertainment, fiction, game, poetry, sports, dance));

			
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
			alpha.addTopics(culture, cooking, hobbies, literature, art, entertainment, fiction, game, poetry, sports);
			beta.addFollows(gamma);
			beta.addFollows(alpha);
			userRepo.saveAll(asList(alpha, beta, gamma, delta, epsilon, zeta, eta, theta, iota, kappa, lambda, mu, nu));

			val sculture = Share.builder().title("Culture").by(alpha).time(System.currentTimeMillis()).build();
			val scooking = Share.builder().title("Cooking").by(alpha).time(System.currentTimeMillis()).build();
			val shobbies = Share.builder().title("Hobbies").by(alpha).time(System.currentTimeMillis()).build();
			val sliterature = Share.builder().title("Literature").by(alpha).time(System.currentTimeMillis()).build();
			val sentertainment = Share.builder().title("On Entertainment").by(alpha).time(System.currentTimeMillis()).build();
			val sfiction = Share.builder().title("On Fiction").by(alpha).time(System.currentTimeMillis()).build();
			val sgame = Share.builder().title("On Game").by(alpha).time(System.currentTimeMillis()).build();
			val spoetry = Share.builder().title("On Poetry").by(alpha).time(System.currentTimeMillis()).build();

			val sart = Share.builder().title("On Art").by(beta).about(Arrays.asList(art, game)).time(System.currentTimeMillis()).build();
			val sart2 = Share.builder().title("On Art 2").by(beta).about(Arrays.asList(art, game)).time(System.currentTimeMillis()).build();
			val sdance = Share.builder().title("On Dance").by(gamma).about(Arrays.asList(art, game)).time(System.currentTimeMillis()).build();
			val ssports = Share.builder().title("On Sports").by(zeta).about(Arrays.asList(game)).time(System.currentTimeMillis()).build();
			shareRepo.saveAll(asList(sculture, scooking, shobbies, sliterature, sart, sart2, sentertainment, sfiction, sgame, spoetry, ssports, sdance));

			val swculture = SharedWith.builder().share(sculture).with(alpha).build();
			val swcooking = SharedWith.builder().share(scooking).with(alpha).build();
			val swhobbies = SharedWith.builder().share(shobbies).with(alpha).build();
			val swliterature = SharedWith.builder().share(sliterature).with(beta).build();
			sharedWithRepo.saveAll(asList(swculture, swcooking, swhobbies, swliterature));
			
			val lartl = Likes.builder().user(alpha).share(sart).build();
			val lart2 = Likes.builder().user(gamma).share(sart).build();
			val lart3 = Likes.builder().user(zeta).share(sart).build();
			val lart4 = Likes.builder().user(lambda).share(sart).build();
			val ldance = Likes.builder().user(beta).share(sdance).build();
			val ldance2 = Likes.builder().user(alpha).share(sdance).build();
			val ldance3 = Likes.builder().user(delta).share(sdance).build();
			val lgame = Likes.builder().user(beta).share(ssports).build();
			val lgame2 = Likes.builder().user(mu).share(ssports).build();
			val lgame3 = Likes.builder().user(nu).share(ssports).build();
			val lpoetry = Likes.builder().user(nu).share(spoetry).build();
			val lpoetry2 = Likes.builder().user(mu).share(spoetry).build();
			val llit = Likes.builder().user(mu).share(sliterature).build();
			likesRepo.saveAll(asList(lartl,lart2,lart3,lart4,ldance,ldance2,ldance3,lgame,lgame2,lgame3,lpoetry,lpoetry2,llit));
		};
	}
}
