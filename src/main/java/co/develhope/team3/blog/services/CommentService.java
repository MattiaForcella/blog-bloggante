package co.develhope.team3.blog.services;

import co.develhope.team3.blog.dto.CommentDto;
import co.develhope.team3.blog.payloads.CommentRequest;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.security.auth.message.AuthException;
import java.util.List;


public interface CommentService {

    HttpStatus postComment(CommentRequest commentRequest) throws AuthException;

    ResponseEntity<CommentDto> getCommentDetails (Long id) throws  AuthException;

    ResponseEntity<List<CommentDto>> getFlaggedComments();


    ResponseEntity<List<CommentDto>> getAllArticleComments(Long articleId);

    ResponseEntity<CommentDto> putComment(CommentDto commentDto);

    ResponseEntity<CommentDto> deleteComment(Long comment_id);
}
