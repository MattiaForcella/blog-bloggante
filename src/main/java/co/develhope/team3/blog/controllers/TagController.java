package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.dto.TagDto;
import co.develhope.team3.blog.payloads.TagRequest;
import co.develhope.team3.blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public void getArticleByTags(){}

    @PostMapping("/create")
    public HttpStatus createTag(@RequestBody @Valid TagRequest tagRequest){
        return tagService.createTag(tagRequest);
    }

    @DeleteMapping("")
    public void deleteTag(){}
}
