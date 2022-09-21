package co.develhope.team3.blog.repository;

import co.develhope.team3.blog.models.User;
import it.pasqualecavallo.studentsmaterial.authorization_framework.dao.UserDao;
import it.pasqualecavallo.studentsmaterial.authorization_framework.service.UserDetails;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails getUserByUsername(String username) {

		User user = userRepository.findByUsername(username);
		if(user != null){
			UserDetails userDetails = new UserDetails();
			userDetails.setUserId(user.getId());
			userDetails.setUsername(user.getUsername());
			userDetails.setPassword(user.getPassword());
			userDetails.setRoles(user.getRoles().stream().map(x -> x.getRole()  ).collect(Collectors.toList()));
			return userDetails;
		}
		else return null;
	}

}
