package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.models.user.User;
import co.develhope.team3.blog.repository.UserRepository;
import co.develhope.team3.blog.security.models.UserPrincipal;
import co.develhope.team3.blog.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService, CustomUserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user==null) throw  new RuntimeException("User not found with this username: " + username);
		return UserPrincipal.create(user);
	}

	@Override
	@Transactional
	public UserDetails loadUserById(Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(String.format("User not found with id: %s", id)));

		return UserPrincipal.create(user);
	}
}
