package co.develhope.team3.blog.repository;

import co.develhope.team3.blog.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long > {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);

    User findByEmail(String email);

    User findByActivationCode(String activationCode);



}
