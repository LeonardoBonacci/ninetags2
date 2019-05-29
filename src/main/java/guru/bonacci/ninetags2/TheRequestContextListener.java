package guru.bonacci.ninetags2;

import javax.servlet.annotation.WebListener;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@WebListener
public class TheRequestContextListener extends RequestContextListener {
}