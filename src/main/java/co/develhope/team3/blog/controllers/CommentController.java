package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.dto.CommentDto;
import co.develhope.team3.blog.models.Comment;
import co.develhope.team3.blog.payloads.CommentRequest;
import co.develhope.team3.blog.services.impl.CommentServiceImp;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.HierarchicalSecurity;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.PublicEndpoint;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.RoleSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment/")
public class CommentController {

    @Autowired
    private CommentServiceImp commentServiceImp;

    @HierarchicalSecurity(bottomRole = "ROLE_USER")
    @PostMapping("")
    public HttpStatus postComment(@RequestBody @Valid CommentRequest commentRequest){
        return commentServiceImp.postComment(commentRequest);
    }

    @PublicEndpoint
    @GetMapping("{commentId}/comment-details")
    public ResponseEntity<CommentDto> getCommentFromId (@PathVariable Long id){
        try {
            return  commentServiceImp.getCommentDetails(id);
        } catch (AuthException e) {
            throw new RuntimeException(e);

        }
    }

   /* @RoleSecurity(value = "ROLE_ADMIN")
    @GetMapping()*/


}
