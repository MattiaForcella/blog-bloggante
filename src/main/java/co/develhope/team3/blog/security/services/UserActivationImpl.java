package co.develhope.team3.blog.security.services;

import co.develhope.team3.blog.models.dto.SignupActivationDto;
import co.develhope.team3.blog.models.dto.UserDto;
import co.develhope.team3.blog.models.user.Role;
import co.develhope.team3.blog.models.user.User;
import co.develhope.team3.blog.payloads.response.ApiResponse;
import co.develhope.team3.blog.repository.RoleRepository;
import co.develhope.team3.blog.repository.UserRepository;
import co.develhope.team3.blog.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserActivationImpl {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleService roleService;

    public ApiResponse activate(SignupActivationDto signupActivationDto) throws RuntimeException{

        User user = userRepository.findByActivationCode(signupActivationDto.getActivationCode());
        if (user == null) throw new RuntimeException("user not found");
        user.setIsActiveUser(true);
        user.setActivationCode(null);

        List<Role> roles = roleService.assignDefaulRole(user.getUsername());
        user.setRoles(roles);

        userRepository.save(user);
        UserDto userDto = this.modelMapper.map(user,UserDto.class);
        userDto.setPassword(null);

        return new ApiResponse(
                true,
                "User has been activated",
                userDto
        );

    }
}
