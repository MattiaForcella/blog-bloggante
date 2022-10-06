package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.models.dto.UserDto;
import co.develhope.team3.blog.payloads.request.UserRequest;
import co.develhope.team3.blog.services.UserServiceBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
public class UserController {

    @Autowired
    private UserServiceBlog userServiceBlog;

    //@HierarchicalSecurity(bottomRole = "ROLE_USER")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getProfile(@PathVariable Long userId)
    {
        //@TODO authentication

        return userServiceBlog.getProfile(userId);
    }

    //@HierarchicalSecurity(bottomRole = "ROLE_USER")
    @PutMapping("/edit-about/{id}")
    public ResponseEntity<Void> putAbout(@RequestBody @Valid UserRequest userRequest, @PathVariable Long id) {

        //@TODO authentication
        return userServiceBlog.putAbout(userRequest, id);
    }
    //@RoleSecurity(value = { "ROLE_ADMIN" })
    @PutMapping("/ban-user/{id}")
    public ResponseEntity<Void> putBan(@RequestBody Boolean ban, @PathVariable Long id ) {
        //@TODO authentication

        return userServiceBlog.banUser(ban, id);
    }

    //put	/edit-role
    //@RoleSecurity(value = { "ROLE_ADMIN" })
    @PutMapping("/add-role/{id}")
    public ResponseEntity<Void> putRole(@RequestBody String role, @PathVariable Long id ) {
        //@TODO authentication
            return userServiceBlog.addRole(role,id);
    }

    //@RoleSecurity(value = { "ROLE_ADMIN" })
    @PutMapping("/remove-role/{id}")
    public ResponseEntity<Void> removeRole(@RequestBody String role, @PathVariable Long userId ) {
        //@TODO authentication
        return userServiceBlog.removeRole(role,userId);
    }



    }
