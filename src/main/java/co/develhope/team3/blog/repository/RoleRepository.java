package co.develhope.team3.blog.repository;

import co.develhope.team3.blog.models.user.Role;
import co.develhope.team3.blog.models.user.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleName role);
}
