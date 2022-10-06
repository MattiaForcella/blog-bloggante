package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.payloads.request.UserAdministrationRequest;
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

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserServiceBlog userServiceBlog;
    @Autowired
    private ArticleService articleService;


    @GetMapping("/me")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> getCurrentUser(@CurrentUser UserPrincipal currentUser){

        //@TODO errore 401, non entra nel controller, "message": "Full authentication is required to access this resource"
        UserDto userDto = userServiceBlog.getCurrentUser(currentUser);

        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{username}/profile")
    public ResponseEntity<UserDto> getUSerProfile(@PathVariable(value = "username") String username) {
        UserDto userDto = userServiceBlog.getUserProfile(username);

        return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{username}/article")
    public ResponseEntity<PagedResponse> getArticlesCreatedBy(@PathVariable(value = "username") String username,
                                                           @RequestParam(value = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                                           @RequestParam(value = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        PagedResponse<Article> response = articleService.getArticlesCreatedBy(username, page, size);

        return new ResponseEntity<PagedResponse>(response, HttpStatus.OK);
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> userSettingsForAdmin(@Valid @RequestBody UserAdministrationRequest newUser,
                                           @PathVariable(value = "username") String username, @CurrentUser UserPrincipal currentUser) {

        //@TODO errore 401, non entra nel controller, "message": "Full authentication is required to access this resource"
        UserDto updatedUser = userServiceBlog.updateUser(newUser, username, currentUser);

        return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
    }


}
