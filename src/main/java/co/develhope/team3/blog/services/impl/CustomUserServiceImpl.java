package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.dto.UserDto;
import co.develhope.team3.blog.exceptions.BlogException;
import co.develhope.team3.blog.models.user.Role;
import co.develhope.team3.blog.models.user.User;
import co.develhope.team3.blog.payloads.request.SignupRequest;
import co.develhope.team3.blog.payloads.response.ApiResponse;
import co.develhope.team3.blog.repository.UserRepository;
import co.develhope.team3.blog.services.CustomUserService;
import co.develhope.team3.blog.services.MailNotificationService;
import co.develhope.team3.blog.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomUserServiceImpl implements CustomUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MailNotificationService mailNotificationService;


    @Override
    public void existByUsername(String username) {
        if (userRepository.findByUsername(username) != null ){
            throw new BlogException(HttpStatus.BAD_REQUEST, "username is already in use");
        }
    }

    @Override
    public void existByEmail(String email) {
        if (userRepository.findByEmail(email) != null ){
            throw new BlogException(HttpStatus.BAD_REQUEST, "email is already in use");
        }
    }

    @Override
    public ApiResponse setUserData(SignupRequest signupRequest) {

        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                signupRequest.getPassword()
        );

        List<Role> roles = roleService.defaultRole();
        user.setRoles(roles);

        user.setActivationCode(UUID.randomUUID().toString());
        mailNotificationService.sendActivationEmail(user);

        userRepository.save(user);

        ApiResponse apiResponse = userToResponse(user);

        return null;
    }

    private ApiResponse userToResponse(User user) {

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setRoles(user.getRoles());
        userDto.setUsername(userDto.getUsername());

        ApiResponse apiResponse = new ApiResponse(
                true,
                "user has been created",
                userDto
        );

        return apiResponse;

    }
}
