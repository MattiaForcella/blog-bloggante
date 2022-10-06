
package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.exceptions.BlogException;
import co.develhope.team3.blog.models.dto.UserDto;
import co.develhope.team3.blog.models.user.RoleName;
import co.develhope.team3.blog.models.user.User;
import co.develhope.team3.blog.payloads.response.ApiResponse;
import co.develhope.team3.blog.repository.RoleRepository;
import co.develhope.team3.blog.repository.UserRepository;
import co.develhope.team3.blog.security.models.CurrentUser;
import co.develhope.team3.blog.security.models.UserPrincipal;
import co.develhope.team3.blog.services.UserServiceBlog;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    public UserDto getCurrentUser(@CurrentUser UserPrincipal principal) {
        User currentUser = userRepository.findByUsername(principal.getUsername());
       return new UserDto(

                currentUser.getId(),
                currentUser.getIsActiveUser(),
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

        UserDto userDto = new UserDto(
                user.getId(),
                user.getIsActiveUser(),
                user.getAbout(),
                user.getEmail(),
                user.getUsername(),
                user.getBan(),
                user.getArticles()
        );
        userDto.setRoles(user.getRoles());

        return userDto;
    }

    @Override
    public UserDto updateUser(User newUser, String username, UserPrincipal currentUser) {
        User user = userRepository.findByUsername(username);
        if (user.getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            user.setBan(newUser.getBan());
            user.setRoles(newUser.getRoles());
            userRepository.save(user);

        }
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

    @Override
    public ApiResponse deleteUser(String username, UserPrincipal currentUser) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new  BlogException(
                    HttpStatus.NOT_FOUND,
                    "user not found"
            );
        }
        if (!user.getId().equals(currentUser.getId()) || !currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete profile of: " + username);
            throw new AccessDeniedException(apiResponse.toString());
        }

        userRepository.deleteById(user.getId());

        return new ApiResponse(Boolean.TRUE, "You successfully deleted profile of: " + username);
    }
}



