package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.dto.ArticleDto;
import co.develhope.team3.blog.dto.TagDto;
import co.develhope.team3.blog.exceptions.ResourceNotFoundException;
import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.Category;
import co.develhope.team3.blog.models.Tag;
import co.develhope.team3.blog.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ModelMapper modelMapper;

}
