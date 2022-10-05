package co.develhope.team3.blog.exceptions;

import org.springframework.http.HttpStatus;

public class BlogException extends RuntimeException {

    private HttpStatus status;

    private String message;

    public BlogException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
