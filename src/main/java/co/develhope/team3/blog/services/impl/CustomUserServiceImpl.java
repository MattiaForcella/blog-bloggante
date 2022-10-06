package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.models.dto.UserDto;
import co.develhope.team3.blog.exceptions.BlogException;
import co.develhope.team3.blog.models.user.Role;
import co.develhope.team3.blog.models.user.User;
import co.develhope.team3.blog.payloads.request.SignupRequest;
import co.develhope.team3.blog.payloads.response.ApiResponse;
import co.develhope.team3.blog.repository.UserRepository;
import co.develhope.team3.blog.services.CustomUserService;
import co.develhope.team3.blog.services.MailNotificationService;
import co.develhope.team3.blog.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder encoder;


    @Override
    public void existByUsername(String username, Boolean condition) {
        if (userRepository.findByUsername(username) != null && !condition){
            throw new BlogException(HttpStatus.BAD_REQUEST, "username is already in use");
        }
        if (userRepository.findByUsername(username) == null && condition){
            throw new BlogException(HttpStatus.BAD_REQUEST, "wrong data");
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
                encoder.encode(signupRequest.getPassword())
        );


        user.setActivationCode(UUID.randomUUID().toString());
        mailNotificationService.sendActivationEmail(user);
        User result = userRepository.save(user);

        ApiResponse apiResponse = userToResponse(user);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
                .buildAndExpand(result.getId()).toUri();

        return apiResponse;
    }

    @Override
    public UserDto userToDto(String username) {

        User user = userRepository.findByUsername(username);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setPassword(null);

        return userDto;
    }

    @Override
    public void userLoginControl(UserDto userDto) {

        if (userDto.getBan()){
            throw new RuntimeException("user banned");
        }

        if (!userDto.getIsActive()){
            throw new RuntimeException("user not active");
        }
    }

    private ApiResponse userToResponse(User user) {

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());

        ApiResponse apiResponse = new ApiResponse(
                true,
                "user has been created",
                userDto
        );

        return apiResponse;

    }
}
