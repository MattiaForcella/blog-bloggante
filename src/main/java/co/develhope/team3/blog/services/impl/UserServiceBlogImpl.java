package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.dto.UserDto;
import co.develhope.team3.blog.models.User;
import co.develhope.team3.blog.payloads.UserRequest;
import co.develhope.team3.blog.repository.UserRepository;
import co.develhope.team3.blog.services.UserServiceBlog;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceBlogImpl implements UserServiceBlog {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;
    public ResponseEntity<UserDto> getProfile(Long id) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        if(principal.getUserId() != user.get().getId())
            return  new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        UserDto userDto = modelMapper.map(user,UserDto.class);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> putAbout(UserRequest userRequest, Long id) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        if(principal.getUserId() != user.get().getId())
            return  new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        user.get().setAbout(userRequest.getAbout());
        userRepository.save(user.get());
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
