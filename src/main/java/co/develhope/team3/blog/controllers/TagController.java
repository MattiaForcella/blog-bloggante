package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public void getArticleByTags(){}

    @PostMapping("/")
    public void createTag(){}

    @PostMapping("/")
    public void insertTag(){}

    @DeleteMapping("")
    public void deleteTag(){}
}
