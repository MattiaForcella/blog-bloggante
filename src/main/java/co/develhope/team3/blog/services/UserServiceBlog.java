package co.develhope.team3.blog.services;


import co.develhope.team3.blog.dto.UserDto;
import co.develhope.team3.blog.payloads.UserRequest;
import org.springframework.http.ResponseEntity;

public interface UserServiceBlog {

    ResponseEntity<Void> banUser(Boolean ban, Long id);

    ResponseEntity<UserDto> getProfile(Long id);

    ResponseEntity<Void> putAbout(UserRequest userRequest, Long id);

    ResponseEntity<Void> addRole(String role, Long id);

    ResponseEntity<Void> removeRole(String role, Long userId);
}