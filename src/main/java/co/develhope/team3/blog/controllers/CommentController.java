package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.models.dto.CommentDto;
import co.develhope.team3.blog.payloads.request.CommentRequest;
import co.develhope.team3.blog.services.impl.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import java.util.List;



@RestController
@RequestMapping("/api/comment/")
public class CommentController {

    @Autowired
    private CommentServiceImp commentServiceImp;

    //@HierarchicalSecurity(bottomRole = "ROLE_USER")
    @PostMapping("")
    public HttpStatus postComment(@RequestBody @Valid CommentRequest commentRequest){
        //@TODO authentication
        return commentServiceImp.postComment(commentRequest);
    }

    //@PublicEndpoint
    @GetMapping("{commentId}/comment-details")
    public ResponseEntity<CommentDto> getCommentFromId (@PathVariable Long id){
        try {
            return  commentServiceImp.getCommentDetails(id);
        } catch (AuthException e) {
            throw new RuntimeException(e);

        }
    }

    //@RoleSecurity(value = "ROLE_ADMIN")
    @GetMapping("/mod")
    private ResponseEntity<List<CommentDto>> getFlaggedComments (){

        //TODO authentication
        return commentServiceImp.getFlaggedComments();
    }

    //@PublicEndpoint()
    @GetMapping("/{articleId}/comment-list")
    public ResponseEntity<List<CommentDto>> getAllArticleComments(@PathVariable() Long articleId){
        return commentServiceImp.getAllArticleComments(articleId);

    }

    //@HierarchicalSecurity(bottomRole = "USER_ROLE")
    @PutMapping("/edit-comment")
    public ResponseEntity<CommentDto> putComment (@RequestBody @Valid CommentDto commentDto){

        //@TODO authentication
        return commentServiceImp.putComment(commentDto);

    }
//
    //@HierarchicalSecurity(bottomRole = "USER_ROLE")
    @DeleteMapping("/api/rule/delete-comment/{id}" )
    public ResponseEntity<CommentDto> deleteComment(@PathVariable Long comment_id){
        //@TODO authentication
        return commentServiceImp.deleteComment(comment_id);
    }


}
