package co.develhope.team3.blog.services;

import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.message.AuthException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public interface FileService {
    String uploadImage(String path, MultipartFile image, AuthenticationContext.Principal principal, Long userId) throws IOException, AuthException;

    InputStream getResource(String path, String fileName) throws FileNotFoundException;
}
