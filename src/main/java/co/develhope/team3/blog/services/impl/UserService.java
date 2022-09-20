package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.models.User;
import co.develhope.team3.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User infoUser(Long id) {
        User user = userRepository.getReferenceById(id);
        System.out.println(user.toString());
        return user;
    }
}
