/**
 * 
 */
package com.fidelity.finalAPI.repositories;

import org.springframework.data.repository.CrudRepository;

import com.fidelity.finalAPI.model.PostComment;


public interface PostCommentRepository extends CrudRepository<PostComment, Long> {

	
}
