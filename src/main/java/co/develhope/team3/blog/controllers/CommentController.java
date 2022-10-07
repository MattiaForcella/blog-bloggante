package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.models.Comment;
import co.develhope.team3.blog.models.dto.CommentDto;
import co.develhope.team3.blog.payloads.request.CommentRequest;
import co.develhope.team3.blog.payloads.response.ApiResponse;
import co.develhope.team3.blog.payloads.response.CommentResponse;
import co.develhope.team3.blog.payloads.response.PagedResponse;
import co.develhope.team3.blog.security.models.CurrentUser;
import co.develhope.team3.blog.security.models.UserPrincipal;
import co.develhope.team3.blog.services.CommentService;
import co.develhope.team3.blog.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;



@RestController
@RequestMapping("/api/articles")
public class CommentController {

    @Autowired
    private CommentService commentService;



    @PostMapping("/{articleId}/comments")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CommentResponse> postComment(@RequestBody @Valid CommentRequest commentRequest,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @PathVariable Long articleId){
        return commentService.postComment(commentRequest,currentUser, articleId );
    }

    @GetMapping("/mod")
    private ResponseEntity<List<CommentDto>> getFlaggedComments (){

        //TODO authentication
        return commentService.getFlaggedComments();
    }

    @GetMapping("/{articleId}/comment-list")
    public ResponseEntity<PagedResponse<Comment>>
    getAllArticleComments(@PathVariable() Long articleId,
                          @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size){

        //@TODO VA IN LOOP
        PagedResponse<Comment> comments = commentService.getAllArticleComments(articleId,page,size);
        return new ResponseEntity<PagedResponse<Comment>>(comments, HttpStatus.OK);

    }


    @PutMapping("/{articleId}/edit-comment/{commentId}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Comment> putComment (@RequestBody @Valid CommentDto commentDto,
                                                  @PathVariable Long articleId,
                                                  @PathVariable Long commentId,
                                                  @CurrentUser UserPrincipal userPrincipal){

        Comment updatedComment = commentService.putComment(articleId, commentId, commentDto, userPrincipal);

        //@TODO modifica il commento ma la risposta va in loop
        return new ResponseEntity<Comment>(updatedComment, HttpStatus.OK);

    }


    @DeleteMapping("/{articleId}/delete-comment/{commentId}" )
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId,
                                                     @PathVariable Long articleId,
                                                     @CurrentUser UserPrincipal userPrincipal){

        ApiResponse response = commentService.deleteComment(articleId, commentId, userPrincipal);

        return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
    }

}
