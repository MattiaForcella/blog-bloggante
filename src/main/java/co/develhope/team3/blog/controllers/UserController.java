package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.models.user.User;
import co.develhope.team3.blog.payloads.response.ApiResponse;
import co.develhope.team3.blog.utils.AppConstants;
import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.dto.UserDto;
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
import java.security.Principal;

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
    public ResponseEntity<UserDto> userSettingsForAdmin(@Valid @RequestBody User newUser,
                                           @PathVariable(value = "username") String username, @CurrentUser UserPrincipal currentUser) {
        UserDto updatedUser = userServiceBlog.updateUser(newUser, username, currentUser);

        return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable(value = "username") String username,
                                                  @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = userServiceBlog.deleteUser(username, currentUser);

        return new ResponseEntity< >(apiResponse, HttpStatus.OK);
    }
}
