package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.config.AppConstants;
import co.develhope.team3.blog.dto.ArticleDto;
import co.develhope.team3.blog.dto.CommentDto;
import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.Comment;
import co.develhope.team3.blog.models.User;
import co.develhope.team3.blog.payloads.CommentRequest;
import co.develhope.team3.blog.repository.ArticleRepository;
import co.develhope.team3.blog.repository.CommentRepository;
import co.develhope.team3.blog.repository.UserRepository;
import co.develhope.team3.blog.services.CommentService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private Comment comment;

    @Autowired
    private User user;
    @Autowired
    private UserRepository userRepository;




    @Override
    public HttpStatus postComment(CommentRequest commentRequest) {
        AuthenticationContext.Principal principal = AuthenticationContext.get();

        Optional<Article> article = articleRepository.findById(commentRequest.getArticleId());
        if(!article.isPresent()) return HttpStatus.BAD_REQUEST;
        comment.setArticle(article.get());
        Optional<User> user = userRepository.findById(principal.getUserId());
        if(!user.isPresent()) return HttpStatus.UNAUTHORIZED;
        comment.setUser(user.get());
        comment.setContent(commentRequest.getContent());
        comment.setCreatedOn(System.currentTimeMillis());
        commentRepository.save(comment);
        return HttpStatus.OK;
    }

    @Override
    public ResponseEntity<CommentDto> getCommentDetails(Long id) throws AuthException {

        Optional<Comment> comment1 = commentRepository.findById(id);
        if(!comment1.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        CommentDto commentDto = modelMapper.map(comment1.get(), CommentDto.class);
        commentDto.setCreateOnWithMill(comment1.get().getCreatedOn());
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<CommentDto>> getFlaggedComments() {

        Pageable p = PageRequest.of(Integer.parseInt(AppConstants.PAGE_NUMBER),
                Integer.parseInt(AppConstants.PAGE_SIZE),
                Sort.by("flag").descending());
        Page<Comment> pageComment = this.commentRepository.findAll(p);
        List<Comment> allComments = pageComment.getContent();

        List<CommentDto> commentDtos = allComments.stream().map((comment) -> this.modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
        for(int i = 0; i< allComments.size(); i++){
            Comment comment1 = allComments.get(i);
            CommentDto commentDto = commentDtos.get(i);
            commentDto.setCreateOnWithMill(comment1.getCreatedOn());
        }
        return new ResponseEntity<>(commentDtos,HttpStatus.OK);

    }


}
