package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.models.user.Role;
import co.develhope.team3.blog.models.user.RoleName;
import co.develhope.team3.blog.models.user.User;
import co.develhope.team3.blog.repository.RoleRepository;
import co.develhope.team3.blog.repository.UserRepository;
import co.develhope.team3.blog.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Role> assignDefaulRole(String username) {
        User user =userRepository.findByUsername(username);

        List<Role> roles = new ArrayList<>();
        if (userRepository.count() <= 1) {
            roles.add(roleRepository.findByName(RoleName.ROLE_USER));
            roles.add(roleRepository.findByName(RoleName.ROLE_EDITOR));
            roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN));
        } else {
            roles.add(roleRepository.findByName(RoleName.ROLE_USER));
        }

            user.setRoles(roles);
            userRepository.save(user);

        return user.getRoles();

    }


}
