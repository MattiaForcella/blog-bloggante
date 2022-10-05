package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.dto.TagDto;
import co.develhope.team3.blog.exceptions.ResourceNotFoundException;
import co.develhope.team3.blog.models.Tag;
import co.develhope.team3.blog.payloads.TagRequest;
import co.develhope.team3.blog.repository.TagRepository;
import co.develhope.team3.blog.services.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<TagDto>> getTagsLike(String name) {
        List<Tag> tagList2 = this.tagRepository.findAllByName("%" + name + "%");
        /*Optional<List<Tag>> tagList = Optional.ofNullable(tagRepository.findAllByName(name));
        if(!tagList.isPresent()) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);*/
        List<TagDto> tagDto1 = tagList2.stream().
                               map((tag) -> this.modelMapper.map(tag, TagDto.class))
                              .collect(Collectors.toList());
        return new ResponseEntity<>(tagDto1,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> createTag(TagRequest tagRequest) {
        Optional<Tag> tag = Optional.ofNullable(tagRepository.findByName(tagRequest.getName()));
        if(tag.isPresent()) return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        tagRepository.save(tag.get());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @Override
    public TagDto deleteTag(Long tagId){
        Optional<Tag> tag = Optional.ofNullable(this.tagRepository.findById(tagId)
                            .orElseThrow(() -> new ResourceNotFoundException("Tag", "tag_id", tagId)));
        TagDto TagDto = this.modelMapper.map(tag, TagDto.class);

        this.tagRepository.delete(tag.get());
        return TagDto;
    }
}
