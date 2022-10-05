package co.develhope.team3.blog.services;

import co.develhope.team3.blog.payloads.request.SignupRequest;
import co.develhope.team3.blog.payloads.response.ApiResponse;

public interface CustomUserService {
    void existByUsername(String username);

    void existByEmail(String email);

    ApiResponse setUserData(SignupRequest signupRequest);
}
