package co.develhope.team3.blog.repository;

import co.develhope.team3.blog.models.user.Role;
import co.develhope.team3.blog.models.user.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleName name);
}
