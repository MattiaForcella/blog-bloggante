package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.dto.TagDto;
import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.Tag;
import co.develhope.team3.blog.payloads.TagRequest;
import co.develhope.team3.blog.repository.TagRepository;
import co.develhope.team3.blog.services.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private Tag tag;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TagDto getArticleByTags(String name, Article article) {

        return null;
    }

    @Override
    public HttpStatus createTag(TagRequest tagRequest) {
        Optional<Tag> tag = Optional.ofNullable(tagRepository.findByName(tagRequest.getName()));
        if(tag.isPresent()) return HttpStatus.ACCEPTED;
        tagRepository.save(tag.get());
        return HttpStatus.OK;
    }

    @Override
    public TagDto deleteTag(Long id, String name) {
        return null;
    }
}
