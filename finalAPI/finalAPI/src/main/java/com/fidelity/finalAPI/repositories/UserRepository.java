/**
 * 
 */
package com.fidelity.finalAPI.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import com.fidelity.finalAPI.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public Optional<User> getByUsername(String username);

}
