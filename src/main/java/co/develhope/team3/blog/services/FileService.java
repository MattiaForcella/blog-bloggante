
package co.develhope.team3.blog.services;

import co.develhope.team3.blog.security.models.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.message.AuthException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public interface FileService {
    String uploadImage(String path, MultipartFile image, UserPrincipal principal, Long userId) throws IOException, AuthException;
    InputStream getResource(String path, String fileName) throws FileNotFoundException;
}


