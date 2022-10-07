package co.develhope.team3.blog.utils;

import co.develhope.team3.blog.exceptions.BlogException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class Utilities {

    public void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BlogException(HttpStatus.BAD_REQUEST, "Page number cannot be less than zero.");
        }

        if (size < 0) {
            throw new BlogException(HttpStatus.BAD_REQUEST, "Size number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BlogException(HttpStatus.BAD_REQUEST, "Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
