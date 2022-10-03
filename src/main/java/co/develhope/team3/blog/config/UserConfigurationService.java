package co.develhope.team3.blog.config;

import co.develhope.team3.blog.repository.UserRepository;
import it.pasqualecavallo.studentsmaterial.authorization_framework.dao.UserDao;
import it.pasqualecavallo.studentsmaterial.authorization_framework.service.UserDetails;
import it.pasqualecavallo.studentsmaterial.authorization_framework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;


public class UserConfigurationService implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDao userDao;
    @Bean
    public UserDetails checkUserCredentials(String username, String password) {
        if (this.userDao == null) {
            Assert.notNull(this.userDao, "userDao is null. Define a UserDao implementation as a Spring Bean");
        }
        if(userRepository.findByUsername(username).getBan()) return null;
        else return this.userDao.getUserByUsername(username);
    }

}
