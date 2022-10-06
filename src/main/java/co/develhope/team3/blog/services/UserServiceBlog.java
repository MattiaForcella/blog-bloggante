package co.develhope.team3.blog.services;


import co.develhope.team3.blog.models.dto.UserDto;
import co.develhope.team3.blog.payloads.request.UserAdministrationRequest;
import co.develhope.team3.blog.payloads.response.ApiResponse;
import co.develhope.team3.blog.security.models.UserPrincipal;

import javax.validation.Valid;


public interface UserServiceBlog {

    UserDto getCurrentUser(UserPrincipal currentUser);

    UserDto getUserProfile(String username);

    UserDto updateUser(@Valid UserAdministrationRequest newUser, String username, UserPrincipal currentUser);

}