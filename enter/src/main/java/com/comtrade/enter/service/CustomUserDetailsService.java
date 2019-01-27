/**
 * 
 */
package com.comtrade.enter.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.comtrade.enter.model.CustomUserDetails;
import com.comtrade.enter.model.User;
import com.comtrade.enter.repository.UserRepository;

/**
 * @author Jovan
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByUsername(username);

		optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

		return optionalUser.map(CustomUserDetails::new).get();
	}
}
