package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.dto.UserDto;
import co.develhope.team3.blog.payloads.UserRequest;
import co.develhope.team3.blog.services.UserServiceBlog;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.HierarchicalSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
public class UserController {
    /*get	/get-profile
    put	/edit-profile
    put	/ban-user
    put	/edit-role
    */
    @Autowired
    private UserServiceBlog userServiceBlog;

    @HierarchicalSecurity(bottomRole = "ROLE_USER")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getProfile(@PathVariable Long userId)
    {
        return userServiceBlog.getProfile(userId);
    }

    @HierarchicalSecurity(bottomRole = "ROLE_USER")
    @PutMapping("/edit-about/{id}")
    public ResponseEntity<Void> putAbout(@RequestBody @Valid UserRequest userRequest, @PathVariable Long id){
        return userServiceBlog.putAbout(userRequest,id);
    }
}
