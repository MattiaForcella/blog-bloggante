package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.models.dto.ArticleDto;
import co.develhope.team3.blog.models.dto.CategoryDto;
import co.develhope.team3.blog.models.dto.CommentDto;
import co.develhope.team3.blog.models.dto.UserDto;
import co.develhope.team3.blog.exceptions.ResourceNotFoundException;
import co.develhope.team3.blog.models.*;

import co.develhope.team3.blog.models.user.RoleName;
import co.develhope.team3.blog.models.user.User;
import co.develhope.team3.blog.payloads.response.ArticleResponse;
import co.develhope.team3.blog.payloads.response.PagedResponse;
import co.develhope.team3.blog.repository.*;
import co.develhope.team3.blog.security.models.UserPrincipal;
import co.develhope.team3.blog.services.ArticleService;
import co.develhope.team3.blog.utils.AppConstants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;


import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static co.develhope.team3.blog.utils.AppConstants.CREATED_AT;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TagServiceImpl tagServiceImpl;
    @Autowired
    CommentServiceImpl commentService;



    @Override
    public ArticleDto createArticle(ArticleDto articleDto, Long categoryId, Long userId, UserPrincipal currentUser) {

        User user = this.userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", currentUser.getId()));

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id ", categoryId));

        Article article = this.modelMapper.map(articleDto, Article.class);
        // TODO aggiungere immagine    article.setImageName("default.png");
        article.setCreatedAt(new Date());
        article.setUser(user);

        article.setCategory(category);

        Article newArticle = this.articleRepository.save(article);
        ArticleDto articleDto1 = this.modelMapper.map(newArticle, ArticleDto.class);


        return articleDto1;
    }

    @Override
    public List<ArticleDto> getArticleByUser(Long id) {
        User user = this.userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User ","userId ", id));
        List<Article> articles = this.articleRepository.findByUser(user);

        List<ArticleDto> articleDtos =  articles.stream().
                                        map((article) -> this.modelMapper.map(article, ArticleDto.class))
                                        .collect(Collectors.toList());

        return articleDtos;

    }

    @Override
    public List<ArticleDto> getArticleByCategory(Long id) {

        Category category = this.categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "category id", id));
        List<Article> articles = this.articleRepository.findByCategory(category);

        List<ArticleDto> articleDtos = articles.stream().map(article -> this.modelMapper.map(article, ArticleDto.class))
                .collect(Collectors.toList());

        return articleDtos;
    }

    @Override
    public ArticleResponse getAllArticles(Integer pageNumber, Integer pageSize) {

        validatePageNumberAndSize(pageNumber,pageSize);

        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, CREATED_AT);

        Page<Article> articles = articleRepository.findAll(p);

        List<Article> allArticles = articles.getContent();

        List<ArticleDto> articleDtos = allArticles.stream().map((article) -> this.modelMapper.map(article, ArticleDto.class))
                .collect(Collectors.toList());

        ArticleResponse articleResponse= new ArticleResponse();

        articleResponse.setContent(articleDtos);
        articleResponse.setPageNumber(articles.getNumber());
        articleResponse.setPageSize(articles.getSize());
        articleResponse.setTotalElements(articles.getTotalElements());
        articleResponse.setTotalPages(articles.getTotalPages());
        articleResponse.setLastPage(articles.isLast());

        return articleResponse;
    }

    @Override
    public ArticleDto getArticleById(Long id) {
        Article article = this.articleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Article","article_id", id));
        return this.modelMapper.map(article, ArticleDto.class);
    }

    @Override
    public ArticleDto deleteArticle(Long articleId, UserPrincipal currentUser) throws AccessDeniedException {

        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article ", "article id", articleId));
        ArticleDto articleDto = this.modelMapper.map(article, ArticleDto.class);

        if (article.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_EDITOR.toString())) ) {
            articleRepository.deleteById(articleId);
            return articleDto;
        } else throw new AccessDeniedException("you don't have permission to delete this post");


    }

    @Override
    public ArticleDto updateArticle(@Valid ArticleDto articleDto, Long articleId, UserPrincipal currentUser, Long categoryId) throws AccessDeniedException {

        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", articleId));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id id", categoryId));

        if (article.getUser().getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority((RoleName.ROLE_EDITOR.toString())))) {

            if (articleDto.getIsNews() != null) article.setIsNews(articleDto.getIsNews());
            if (articleDto.getTags() != null) article.setTags(articleDto.getTags());
            if (articleDto.getContent() != null) article.setContent(articleDto.getContent());
            if (articleDto.getTitle() != null) article.setTitle(articleDto.getTitle());

            article.setUpdateOn(new Date());

            Article updateArticle = this.articleRepository.save(article);
            return this.modelMapper.map(updateArticle, ArticleDto.class);
        }
        throw new AccessDeniedException("You don't have permission for update this article");
    }

    @Override
    public ArticleDto updateContentArticle(Long articleId, String content, UserPrincipal userPrincipal) throws AuthException {

        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", articleId));

        if (article.getUser().getId().equals(userPrincipal.getId())
                || userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))
                || userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority((RoleName.ROLE_EDITOR.toString())))) {

            article.setContent(content);
            article.setUpdateOn(new Date());

            Article updateArticle = this.articleRepository.save(article);

            return this.modelMapper.map(updateArticle, ArticleDto.class);
        }
        throw new AuthException("You don't have permission for update content of this article");
    }


    @Override
    public ArticleDto updateCommentArticle(Long articleId , CommentDto commentDto, UserPrincipal userPrincipal) throws AuthException {

        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", articleId));

        if (article.getUser().getId().equals(userPrincipal.getId())
                || userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {

            Category commentUpdate = this.modelMapper.map(commentDto, Category.class);
            article.setCategory(commentUpdate);
            article.setUpdateOn(new Date());
            articleRepository.save(article);

            return this.modelMapper.map(article, ArticleDto.class);
        }
        throw new AuthException("You don't have permission for update comments");
    }

    @Override
    public ArticleDto updateCategoriesArticle(Long articleId, CategoryDto categoryDto, UserPrincipal principal) throws AuthException {

        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", articleId));

        if (article.getUser().getId().equals(principal.getId())
                || principal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))
                || principal.getAuthorities().contains(new SimpleGrantedAuthority((RoleName.ROLE_EDITOR.toString())))) {

            Category categoryUpdate = this.modelMapper.map(categoryDto, Category.class);
            article.setCategory(categoryUpdate);
            article.setUpdateOn(new Date());
            articleRepository.save(article);

            return this.modelMapper.map(article, ArticleDto.class);
        }

        throw new AuthException("You don't have permission for update category of this article ");
    }

    @Override
    public List<ArticleDto> searchPosts(String keyword) {
        List<Article> articles = this.articleRepository.searchByTitle("%"+keyword+"%");
        List<ArticleDto> postDtos = articles.stream().map((post)->this.modelMapper.map(post, ArticleDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public ResponseEntity<List<ArticleDto>> getAllArticlesByIsNews() {
        List<Article> articles = articleRepository.findByIsNews();
        List<ArticleDto> articleDtos = new ArrayList<>();
        for (Article article: articles){
            ArticleDto articleDto = new ArticleDto();
            articleDto.setId(article.getId());
            articleDto.setIsNews(article.getIsNews());
            articleDto.setTags(article.getTags());
            articleDto.setCategory((List<Category>) article.getCategory());
            UserDto userDto = modelMapper.map(article.getUser(), UserDto.class);
            articleDto.setUser(userDto);
            articleDto.setTitle(article.getTitle());
            articleDto.setCreatedOn(article.getCreatedAt());
            articleDto.setUpdateOn(article.getUpdateOn());
            articleDto.setImageName(article.getImageName());

            articleDtos.add(articleDto);
        }
        return new ResponseEntity<>(articleDtos,HttpStatus.OK);
    }

    @Override
    public PagedResponse<Article> getArticlesCreatedBy(String username, Integer page, Integer size) {
        validatePageNumberAndSize(page, size);
        User user = userRepository.findByUsername(username);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, CREATED_AT);
        Page<Article> articles = articleRepository.findByCreatedBy(user.getUsername(), pageable);

        List<Article> content = articles.getNumberOfElements() == 0 ? Collections.emptyList() : articles.getContent();

        return new PagedResponse<>(content, articles.getNumber(), articles.getSize(), articles.getTotalElements(),
                articles.getTotalPages(), articles.isLast());
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new RuntimeException("Page number cannot be less than zero.");
        }

        if (size < 0) {
            throw new RuntimeException("Size number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new RuntimeException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }


}
