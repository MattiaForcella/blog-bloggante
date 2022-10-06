package co.develhope.team3.blog.services;


import co.develhope.team3.blog.models.dto.UserDto;
import co.develhope.team3.blog.models.user.User;
import co.develhope.team3.blog.payloads.response.ApiResponse;
import co.develhope.team3.blog.security.models.UserPrincipal;


public interface UserServiceBlog {

    UserDto getCurrentUser(UserPrincipal currentUser);

    UserDto getUserProfile(String username);

    UserDto updateUser(User newUser, String username, UserPrincipal currentUser);

    ApiResponse deleteUser(String username, UserPrincipal currentUser);
}