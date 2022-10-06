package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.utils.AppConstants;
import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.dto.UserDto;
import co.develhope.team3.blog.payloads.request.UserRequest;
import co.develhope.team3.blog.payloads.response.PagedResponse;
import co.develhope.team3.blog.security.models.CurrentUser;
import co.develhope.team3.blog.security.models.UserPrincipal;
import co.develhope.team3.blog.services.ArticleService;
import co.develhope.team3.blog.services.UserServiceBlog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceBlog userServiceBlog;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UserDto> getCurrentUser(@CurrentUser UserPrincipal currentUser){
        UserDto userDto = userServiceBlog.getCurrentUser(currentUser);

        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<UserDto> getUSerProfile(@PathVariable(value = "username") String username) {
        UserDto userDto = userServiceBlog.getUserProfile(username);

        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{username}/posts")
    public ResponseEntity<PagedResponse> getPostsCreatedBy(@PathVariable(value = "username") String username,
                                                           @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                           @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        PagedResponse<Article> response = articleService.getArticlesCreatedBy(username, page, size);

        return new ResponseEntity<PagedResponse>(response, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> updateUser(@Valid @RequestBody User newUser,
                                           @PathVariable(value = "username") String username, @CurrentUser UserPrincipal currentUser) {
        User updatedUSer = userService.updateUser(newUser, username, currentUser);

        return new ResponseEntity< >(updatedUSer, HttpStatus.CREATED);
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
