package guru.bonacci.ninetags2.repos;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.stereotype.Repository;

import guru.bonacci.ninetags2.domain.Share;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FullTextSearchRepo2 {

	private final SessionFactory sessionFactory;

	public void process() {

		Session session = sessionFactory.openSession();
//		session.purgeDatabase();
		search(session, "culture").forEach(System.out::println);
	}

	public Iterable<Share> search(Session session, String searchQuery) {
	    // The query 
	    String cypherQuery = "CALL db.index.fulltext.queryNodes($index, $query) "; 
	    // Execute query
	    Map<String, Object> params = new HashMap<>();
	    StringBuilder query = new StringBuilder().append("title:").append(searchQuery).append(" OR ").append("description:").append(searchQuery);
	    params.put("query", query.toString());
	    params.put("index", "shares2");
	    return session.query(Share.class, cypherQuery, params);
	} 
}