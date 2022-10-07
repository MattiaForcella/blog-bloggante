package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.models.dto.SignupActivationDto;
import co.develhope.team3.blog.models.dto.UserDto;
import co.develhope.team3.blog.payloads.request.LoginRequest;
import co.develhope.team3.blog.payloads.request.SignupRequest;
import co.develhope.team3.blog.payloads.response.ApiResponse;
import co.develhope.team3.blog.payloads.response.JwtResponse;
import co.develhope.team3.blog.security.jwt.JwtUtils;
import co.develhope.team3.blog.security.services.UserActivationImpl;
import co.develhope.team3.blog.services.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CustomUserService customUserService;

    @Autowired
    private UserActivationImpl activation;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;




    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignupRequest signupRequest) throws Exception {

        customUserService.existByUsername(signupRequest.getUsername(), false);
        customUserService.existByEmail(signupRequest.getEmail());
        ApiResponse apiResponse = customUserService.setUserData(signupRequest);

        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }

    @PostMapping("/activation")
    public ResponseEntity<ApiResponse> activation(@RequestBody SignupActivationDto signupActivationDto)throws Exception{

        ApiResponse apiResponse = activation.activate(signupActivationDto);

        return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> signin(@Valid @RequestBody LoginRequest loginRequest){

        customUserService.existByUsername(loginRequest.getUsername(), true);
        UserDto userDto = customUserService.userToDto(loginRequest.getUsername());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));

        customUserService.userLoginControl(userDto);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        JwtResponse jwtResponse = new JwtResponse(jwt,
                userDto.getId(),
                userDto.getUsername(),
                userDto.getEmail(),
                userDto.getRoles());

        return new ResponseEntity<JwtResponse>(jwtResponse, HttpStatus.OK);

    }
}
