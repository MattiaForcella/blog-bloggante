
package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.models.dto.UserDto;
import co.develhope.team3.blog.models.user.User;
import co.develhope.team3.blog.repository.RoleRepository;
import co.develhope.team3.blog.repository.UserRepository;
import co.develhope.team3.blog.security.models.CurrentUser;
import co.develhope.team3.blog.security.models.UserPrincipal;
import co.develhope.team3.blog.services.UserServiceBlog;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserServiceBlogImpl implements UserServiceBlog {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public UserDto getCurrentUser(@CurrentUser UserPrincipal currentUser) {
       return new UserDto(

                currentUser.getId(),
                currentUser.getActiveUser(),
                currentUser.getAbout(),
                currentUser.getEmail(),
                currentUser.getUsername(),
                currentUser.getBan(),
                currentUser.getArticles()
        );

    }

    @Override
    public UserDto getUserProfile(String username) {

        User user = userRepository.findByUsername(username);

        return new UserDto(
                user.getId(),
                user.getIsActiveUser(),
                user.getAbout(),
                user.getEmail(),
                user.getUsername(),
                user.getBan(),
                user.getArticles()
        );
    }
}



