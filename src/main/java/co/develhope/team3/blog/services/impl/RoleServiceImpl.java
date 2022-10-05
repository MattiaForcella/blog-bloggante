package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.models.user.Role;
import co.develhope.team3.blog.models.user.RoleName;
import co.develhope.team3.blog.repository.RoleRepository;
import co.develhope.team3.blog.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> defaultRole() {

        List<Role> roles = new ArrayList<>();

        roles.add(roleRepository.findByName(RoleName.ROLE_USER));
        if (roles == null) throw new RuntimeException("USER_ROLE_NOT_SET");

        return roles;
    }
}
