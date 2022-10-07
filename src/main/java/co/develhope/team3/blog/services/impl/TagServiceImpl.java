package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.models.dto.TagDto;
import co.develhope.team3.blog.exceptions.ResourceNotFoundException;
import co.develhope.team3.blog.models.Tag;
import co.develhope.team3.blog.models.user.RoleName;
import co.develhope.team3.blog.payloads.request.TagRequest;
import co.develhope.team3.blog.payloads.response.PagedResponse;
import co.develhope.team3.blog.repository.TagRepository;
import co.develhope.team3.blog.security.models.UserPrincipal;
import co.develhope.team3.blog.services.TagService;
import co.develhope.team3.blog.utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Utilities utilities;

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
    public TagDto deleteTag(Long tagId, Long userId, UserPrincipal current) throws AuthException {

        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

        if (userId.equals(current.getId()) || current.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {

            TagDto TagDto = this.modelMapper.map(tag, TagDto.class);
            this.tagRepository.deleteById(tagId);
            return TagDto;
        }
        throw new AuthException("You don't have permission to delete this tag");
    }

    @Override
    public PagedResponse<Tag> getAllTags(Integer page, Integer size) {
        utilities.validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Tag> tags = tagRepository.findAll(pageable);

        List<Tag> content = tags.getNumberOfElements() == 0 ? Collections.emptyList() : tags.getContent();

        return new PagedResponse<Tag>(content, tags.getNumber(), tags.getSize(), tags.getTotalElements(), tags.getTotalPages(), tags.isLast());

    }

    @Override
    public Tag addTag(Tag tag, UserPrincipal principal, Long userId) throws AuthException {

        if (userId.equals(principal.getId())
                || principal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))
                || principal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_EDITOR.toString()))){

            return tagRepository.save(tag);

        }
          throw new AuthException("You don't have permission to add tag") ;

    }

    @Override
    public Tag updateTag(Long userId, Tag newTag, UserPrincipal currentUser, Long tagId) throws AuthException {
        Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
        if (userId.equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_EDITOR.toString()))) {

            tag.setName(newTag.getName());

            return tagRepository.save(tag);

        }

        throw new AuthException("You don't have permission to update this tag");

    }
}
