package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.models.Tag;
import co.develhope.team3.blog.models.dto.TagDto;
import co.develhope.team3.blog.payloads.request.TagDeleteRequest;
import co.develhope.team3.blog.payloads.request.TagRequest;
import co.develhope.team3.blog.payloads.response.ApiResponse;
import co.develhope.team3.blog.payloads.response.PagedResponse;
import co.develhope.team3.blog.repository.TagRepository;
import co.develhope.team3.blog.security.models.CurrentUser;
import co.develhope.team3.blog.security.models.UserPrincipal;
import co.develhope.team3.blog.services.TagService;
import co.develhope.team3.blog.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;


    @GetMapping("/alltag")
    public ResponseEntity<PagedResponse<Tag>> getAllTags(
            @RequestParam(name = "page", required = false, defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(name = "size", required = false, defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size){

        //@TODO DA TESTARE
        PagedResponse<Tag> response = tagService.getAllTags(page, size);

        return new ResponseEntity<PagedResponse<Tag>>(response, HttpStatus.OK);
    }

    @PostMapping("/{userId}/create-tag")
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Tag> createTag(@RequestBody @Valid Tag tag,
                                          @CurrentUser UserPrincipal principal,
                                          @PathVariable Long userId) throws AuthException {
        //@TODO DA TESTARE

        Tag newTag = tagService.addTag(tag, principal, userId);

        return new ResponseEntity<Tag>(newTag,HttpStatus.CREATED);
    }

    @PutMapping("/{userId}/tag/{tagId}")
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Tag> updateTag(@PathVariable Long userId,
                                         @Valid @RequestBody Tag tag,
                                         @CurrentUser UserPrincipal currentUser,
                                         @PathVariable Long tagId) throws AuthException {

        //@TODO DA TESTARE

        Tag updatedTag = tagService.updateTag(userId, tag, currentUser, tagId);

        return new ResponseEntity< >(updatedTag, HttpStatus.OK);
    }

    @DeleteMapping("{userId}/delete/{tagId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TagDeleteRequest> deleteTag(@PathVariable Long tagId,
                                                 @PathVariable Long userId,
                                                 @CurrentUser UserPrincipal current) throws AuthException {
        //@TODO DA TESTARE!!!
        TagDto tagDeleted = this.tagService.deleteTag(tagId, userId ,current);

        return new ResponseEntity<TagDeleteRequest>( new TagDeleteRequest("Tag is sucessfully deleted",
                                                          tagDeleted),
                                                          HttpStatus.OK);
    }
}
