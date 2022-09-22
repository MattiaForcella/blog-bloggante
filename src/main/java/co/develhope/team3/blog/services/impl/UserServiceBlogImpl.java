package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.models.User;
import co.develhope.team3.blog.repository.UserRepository;
import co.develhope.team3.blog.services.UserServiceBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceBlogImpl implements UserServiceBlog {
    @Autowired
    private UserRepository userRepository;
    public User infoUser(Long id) {
        User user = userRepository.getReferenceById(id);
        System.out.println(user.toString());
        return user;
    }
}
