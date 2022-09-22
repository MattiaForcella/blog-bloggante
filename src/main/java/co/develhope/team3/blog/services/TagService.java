package co.develhope.team3.blog.services;

import co.develhope.team3.blog.dto.TagDto;
import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.payloads.TagRequest;
import org.springframework.http.HttpStatus;

public interface TagService {

    TagDto getArticleByTags(String name, Article article);

    HttpStatus createTag (TagRequest tagRequest);

    TagDto deleteTag (Long id, String name);
}
