package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.dto.TagDto;
import co.develhope.team3.blog.payloads.TagDeleteRequest;
import co.develhope.team3.blog.payloads.TagRequest;
import co.develhope.team3.blog.repository.TagRepository;
import co.develhope.team3.blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    /*
    @Autowired
    private UtilsService utilsService;

     */


    //@PublicEndpoint
    @GetMapping("/alltag")
    public ResponseEntity<List<TagDto>> getTagsLike(@RequestParam String name){
        return tagService.getTagsLike(name);
    }

    //@HierarchicalSecurity(bottomRole = "ROLE_EDITOR")
    @PostMapping("/create")
    public ResponseEntity<Void> createTag(@RequestBody @Valid TagRequest tagRequest){

        //@TODO authentication
        return tagService.createTag(tagRequest);
    }

    //@HierarchicalSecurity(bottomRole = "ROLE_ADMIN")
    @DeleteMapping("/delete/{tagId}")
    public ResponseEntity<TagDeleteRequest> deleteTag(@PathVariable Long tagId) throws AuthException {
        //@TODO authentication
        TagDto tagDeleted = this.tagService.deleteTag(tagId);
        return new ResponseEntity<TagDeleteRequest>( new TagDeleteRequest("Tag is sucessfully deleted",
                                                          tagDeleted),
                                                          HttpStatus.OK);
    }
}
