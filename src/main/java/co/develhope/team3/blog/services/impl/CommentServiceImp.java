package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.models.user.User;
import co.develhope.team3.blog.payloads.response.CommentResponse;
import co.develhope.team3.blog.security.models.UserPrincipal;
import co.develhope.team3.blog.utils.AppConstants;
import co.develhope.team3.blog.models.dto.CommentDto;
import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.Comment;
import co.develhope.team3.blog.payloads.request.CommentRequest;
import co.develhope.team3.blog.repository.ArticleRepository;
import co.develhope.team3.blog.repository.CommentRepository;
import co.develhope.team3.blog.repository.UserRepository;
import co.develhope.team3.blog.services.CommentService;
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
    private UserRepository userRepository;






    public ResponseEntity<CommentResponse> postComment(CommentRequest commentRequest, UserPrincipal currentUser, Long articleId) {


        //AuthenticationContext.Principal principal = AuthenticationContext.get();

        Comment comment = new Comment();
        Optional<Article> article = articleRepository.findById(articleId);
        if(!article.isPresent()) return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        comment.setArticle(article.get());
        Optional<User> user = userRepository.findById(currentUser.getId());
        if(!user.isPresent()) return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
        comment.setUser(user.get());
        comment.setContent(commentRequest.getContent());
        comment.setCreatedOn(System.currentTimeMillis());
        this.commentRepository.save(comment);
        return new ResponseEntity<CommentResponse>(new CommentResponse(true , "Comment has been published") ,HttpStatus.OK);
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

        Pageable p = PageRequest.of(Integer.parseInt(AppConstants.DEFAULT_PAGE_SIZE),
                Integer.parseInt(AppConstants.DEFAULT_PAGE_SIZE),
                Sort.by("flag").descending());
        Page<Comment> pageComment = commentRepository.findAllFlaggedComments(p);
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

    @Override
    public ResponseEntity<List<CommentDto>> getAllArticleComments(Long articleId) {
        Pageable p = PageRequest.of(Integer.parseInt(AppConstants.DEFAULT_PAGE_NUMBER),
                Integer.parseInt(AppConstants.DEFAULT_PAGE_SIZE));
        Page<Comment> pageComment = commentRepository.findAllByArticleId(articleId, p);
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

    @Override
    public ResponseEntity<CommentDto> putComment(CommentDto commentDto) {
        /*
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        if(!principal.getUserId().equals(commentDto.getUser().getId()))
            return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);

         */
        Long data = commentDto.getCreatedOn().getTime();
        Comment comment = modelMapper.map(commentDto, Comment.class);
        commentRepository.save(comment);

        return new ResponseEntity<>(commentDto,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<CommentDto> deleteComment(Long comment_id) {
        Optional<Comment> comment = Optional.of(commentRepository.getReferenceById(comment_id));
        /*
        if(!comment.isPresent())
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        if((comment.get().getId() != principal.getUserId()) || !principal.getRoles().contains("ROLE_ADMIN") )
            return  new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

         */
        commentRepository.delete(comment.get());
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);

    }


}
