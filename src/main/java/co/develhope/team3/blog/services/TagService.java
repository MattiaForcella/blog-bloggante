package co.develhope.team3.blog.services;

import co.develhope.team3.blog.models.Tag;
import co.develhope.team3.blog.models.dto.TagDto;
import co.develhope.team3.blog.payloads.request.TagRequest;
import co.develhope.team3.blog.payloads.response.PagedResponse;
import co.develhope.team3.blog.security.models.UserPrincipal;
import org.springframework.http.ResponseEntity;

import javax.security.auth.message.AuthException;
import java.util.List;


public interface TagService {


    ResponseEntity<List<TagDto>> getTagsLike(String name);

    ResponseEntity<Void> createTag (TagRequest tagRequest);

    TagDto deleteTag(Long id, Long userId, UserPrincipal current) throws AuthException;

    PagedResponse<Tag> getAllTags(Integer page, Integer size);

    Tag addTag(Tag tag, UserPrincipal principal, Long userId) throws AuthException;

    Tag updateTag(Long userId, Tag tag, UserPrincipal currentUser, Long tagId) throws AuthException;
}
