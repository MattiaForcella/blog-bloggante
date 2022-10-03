package co.develhope.team3.blog.repository;

import co.develhope.team3.blog.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRoleRepository extends JpaRepository<Role,Long> {
}
