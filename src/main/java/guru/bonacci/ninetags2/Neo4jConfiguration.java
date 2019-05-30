package guru.bonacci.ninetags2;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.annotation.EnableNeo4jAuditing;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jAuditing
@EnableNeo4jRepositories({"guru.bonacci.ninetags2.repos", "guru.bonacci.ninetags2.experimental"})
@EnableTransactionManagement
public class Neo4jConfiguration {

}
