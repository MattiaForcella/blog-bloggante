package co.develhope.team3.blog.services;

import co.develhope.team3.blog.dto.TagDto;
import co.develhope.team3.blog.models.Article;

public interface TagService {

    TagDto getArticleByTags(String name, Article article);

    TagDto createTag (Long id, String name, Article article);

    TagDto insertTag (String name);

    TagDto deleteTag (Long id, String name);
}
