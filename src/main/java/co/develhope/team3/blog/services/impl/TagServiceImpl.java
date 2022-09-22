package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.dto.TagDto;
import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.repository.TagRepository;
import co.develhope.team3.blog.services.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TagDto getArticleByTags(String name, Article article) {
        return null;
    }

    @Override
    public TagDto createTag(Long id, String name, Article article) {
        return null;
    }

    @Override
    public TagDto insertTag(String name) {
        return null;
    }

    @Override
    public TagDto deleteTag(Long id, String name) {
        return null;
    }
}
