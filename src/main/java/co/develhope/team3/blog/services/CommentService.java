package co.develhope.team3.blog.services;

import co.develhope.team3.blog.models.Comment;
import co.develhope.team3.blog.models.dto.CommentDto;
import co.develhope.team3.blog.payloads.request.CommentRequest;
import co.develhope.team3.blog.payloads.response.ApiResponse;
import co.develhope.team3.blog.payloads.response.CommentResponse;
import co.develhope.team3.blog.payloads.response.PagedResponse;
import co.develhope.team3.blog.security.models.UserPrincipal;
import org.springframework.http.ResponseEntity;

import javax.security.auth.message.AuthException;
import java.util.List;


public interface CommentService {

    ResponseEntity<CommentResponse> postComment(CommentRequest commentRequest, UserPrincipal currentUser, Long articleId);

    ResponseEntity<CommentDto> getCommentDetails (Long id) throws  AuthException;

    ResponseEntity<List<CommentDto>> getFlaggedComments();


    PagedResponse<Comment> getAllArticleComments(Long articleId, Integer page, Integer size);

    Comment putComment(Long articleId, Long commentId, CommentDto commentDto, UserPrincipal userPrincipal);

    ApiResponse deleteComment(Long articleId, Long commentId, UserPrincipal userPrincipal);
}
