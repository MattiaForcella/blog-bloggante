package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.services.UtilsService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;

@Service
public class UtilsServiceImpl implements UtilsService {
    @Override
    public void authControl(Long userId, AuthenticationContext.Principal principal) throws AuthException {
        if (!principal.getUserId().equals(userId)) {
            throw new AuthException("Unauthorized user, user_id mismatch");
        }
    }


}
