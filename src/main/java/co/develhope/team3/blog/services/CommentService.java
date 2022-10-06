package co.develhope.team3.blog.services;

import co.develhope.team3.blog.models.dto.CommentDto;
import co.develhope.team3.blog.payloads.request.CommentRequest;
import co.develhope.team3.blog.payloads.response.CommentResponse;
import co.develhope.team3.blog.security.models.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.security.auth.message.AuthException;
import java.util.List;


public interface CommentService {

    ResponseEntity<CommentResponse> postComment(CommentRequest commentRequest, UserPrincipal currentUser, Long articleId);

    ResponseEntity<CommentDto> getCommentDetails (Long id) throws  AuthException;

    ResponseEntity<List<CommentDto>> getFlaggedComments();


    ResponseEntity<List<CommentDto>> getAllArticleComments(Long articleId);

    ResponseEntity<CommentDto> putComment(CommentDto commentDto);

    ResponseEntity<CommentDto> deleteComment(Long comment_id);
}
