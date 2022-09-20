package co.develhope.team3.blog.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    String uploadImage(String path, MultipartFile image) throws IOException;

    InputStream getResource(String path, String fileName) throws FileNotFoundException;
}
