
package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.models.user.RoleName;
import co.develhope.team3.blog.security.models.UserPrincipal;
import co.develhope.team3.blog.services.FileService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.message.AuthException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    public String uploadImage(String path, MultipartFile file, UserPrincipal principal, Long userId) throws IOException, AuthException {

        if (userId.equals(principal.getId())
                || principal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))
                || principal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_EDITOR.toString())) ) {


            String name = file.getOriginalFilename();

            String randomID = UUID.randomUUID().toString();
            String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));

            String filePath = path + File.separator + fileName1;


            File f = new File(path);
            if (!f.exists()) {
                f.mkdir();
            }

            Files.copy(file.getInputStream(), Paths.get(filePath));

            return fileName1;
        }

        throw  new AuthException("You don't have permission to update image of this article");
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        // db logic to return inpustream
        return is;
    }
}


