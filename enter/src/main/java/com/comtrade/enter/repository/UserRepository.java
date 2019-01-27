/**
 * 
 */
package com.comtrade.enter.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.comtrade.enter.model.User;

/**
 * @author Jovan
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);

}
