package guru.bonacci.ninetags2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import guru.bonacci.ninetags2.repos.UserRepository;
import guru.bonacci.ninetags2.web.FakeSecurityContext;
import guru.bonacci.ninetags2.web.UserInterceptor;

@EnableWebMvc
@Configuration
@EnableNeo4jRepositories({"guru.bonacci.ninetags2.repos", "guru.bonacci.ninetags2.experimental"})
@EnableTransactionManagement
public class WebAppConfig implements WebMvcConfigurer {

	@Autowired @Lazy
	UserRepository userRepo;

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(new UserInterceptor(securityContext()));
	}

	@Bean
	@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public FakeSecurityContext securityContext() {
		return new FakeSecurityContext(userRepo);
	}
}