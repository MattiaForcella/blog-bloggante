package co.develhope.team3.blog.services;

import co.develhope.team3.blog.dto.TagDto;
import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.payloads.TagRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagService {


    ResponseEntity<List<TagDto>> getTagsLike(String name);

    ResponseEntity<Void> createTag (TagRequest tagRequest);

    TagDto deleteTag(Long id);
}
