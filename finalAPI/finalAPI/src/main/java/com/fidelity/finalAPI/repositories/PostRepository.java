/**
 * 
 */
package com.fidelity.finalAPI.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.fidelity.finalAPI.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

	
	
	public List<Post> getByTitle(String title);
	
	public List<Post> getByPublished(Boolean published);
	
	@Query(value = "SELECT DISTINCT P "
			+ " FROM Post P "
			+ " 	 JOIN P.tags T "
			+ " WHERE upper(T.name) = upper(:pTag) ")
	public List<Post> findPostsByTag(@Param("pTag") String tag);
	
	@Query(value = "SELECT DISTINCT P "
			+ "FROM Post P "
			+ "		JOIN P.categories C "
			+ "WHERE upper(C.name) = upper(:pCategory) ")
	public List<Post> findPostsByCategory(@Param("pCategory") String category);
}
