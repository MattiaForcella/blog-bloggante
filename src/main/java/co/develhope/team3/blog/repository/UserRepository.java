package co.develhope.team3.blog.repository;

import co.develhope.team3.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User, Long > {

    User findByUsername(String username);
}
