package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.dto.ArticleDto;
import co.develhope.team3.blog.dto.CategoryDto;
import co.develhope.team3.blog.dto.CommentDto;
import co.develhope.team3.blog.exceptions.ResourceNotFoundException;
import co.develhope.team3.blog.models.*;
import co.develhope.team3.blog.payloads.ArticleResponse;
import co.develhope.team3.blog.repository.*;
import co.develhope.team3.blog.services.ArticleService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import javax.security.auth.message.AuthException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private CommentServiceImp commentServiceImp;


    @Override
    public ArticleDto createArticle(ArticleDto articleDto, Long userId, Long categoryId, AuthenticationContext.Principal principal) throws AuthException {


        if (!principal.getUserId().equals(userId)) {
            throw new AuthException("Unauthorized editor, editor_id mismatch");
        }

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User id", userId));

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id ", categoryId));

        Article article = this.modelMapper.map(articleDto, Article.class);
        article.setImageName("default.png");
        article.setCreatedOn(new Date());
        article.setUser(user);
        article.setCategory(category);

        Article newArticle = this.articleRepository.save(article);

        return this.modelMapper.map(newArticle, ArticleDto.class);
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
    public ArticleResponse getAllArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Article> pageArticle = this.articleRepository.findAll(p);

        List<Article> allArticles = pageArticle.getContent();

        List<ArticleDto> articleDtos = allArticles.stream().map((article) -> this.modelMapper.map(article, ArticleDto.class))
                .collect(Collectors.toList());

        ArticleResponse articleResponse= new ArticleResponse();

        articleResponse.setContent(articleDtos);
        articleResponse.setPageNumber(pageArticle.getNumber());
        articleResponse.setPageSize(pageArticle.getSize());
        articleResponse.setTotalElements(pageArticle.getTotalElements());
        articleResponse.setTotalPages(pageArticle.getTotalPages());
        articleResponse.setLastPage(pageArticle.isLast());

        return articleResponse;
    }

    @Override
    public ArticleDto getArticleById(Long id) {
        Article article = this.articleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Article","article_id", id));
        return this.modelMapper.map(article, ArticleDto.class);
    }

    @Override
    public ArticleDto deleteArticle(Long id, AuthenticationContext.Principal principal, Long userId) throws AuthException {

        if (!principal.getUserId().equals(userId)) {
            throw new AuthException("Unauthorized editor, editor_id mismatch");
        }

        Article article = this.articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article ", "article id", id));
        ArticleDto articleDto = this.modelMapper.map(article, ArticleDto.class);

        this.articleRepository.delete(article);
        return articleDto;
    }

    @Override
    public ArticleDto updateArticle(ArticleDto articleDto, Long articleId, Long userId, AuthenticationContext.Principal principal) throws AuthException {

        if (!principal.getUserId().equals(userId)) {
            throw new AuthException("Unauthorized editor, editor_id mismatch");
        }

        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", articleId));
        article.setIsNews(articleDto.getIsNews());
        article.setImageName(articleDto.getImageName());
        article.setUpdateOn(new Date());

        Article updateArticle = this.articleRepository.save(article);
        return this.modelMapper.map(updateArticle, ArticleDto.class);
    }

    @Override
    public ArticleDto updateTitleArticle(Long articleId, Long userId, AuthenticationContext.Principal principal, String title) throws AuthException {

        if (!principal.getUserId().equals(userId)) {
            throw new AuthException("Unauthorized editor, editor_id mismatch");
        }

        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", articleId));
        article.setTitle(title);

        article.setUpdateOn(new Date());
        Article updateArticle = this.articleRepository.save(article);
        return this.modelMapper.map(updateArticle,ArticleDto.class);
    }

    @Override
    public ArticleDto updateContentArticle(Long articleId, String content, Long userId, AuthenticationContext.Principal principal) throws AuthException {

        if (!principal.getUserId().equals(userId)) {
            throw new AuthException("Unauthorized editor, editor_id mismatch");
        }
        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", articleId));
        article.setContent(content);

        article.setUpdateOn(new Date());
        Article updateArticle = this.articleRepository.save(article);
        return this.modelMapper.map(updateArticle, ArticleDto.class);
    }

    @Override
    public ArticleDto updateNewsArticle(Long articleId, Long userId, Boolean news, AuthenticationContext.Principal principal) throws AuthException {

        if (!principal.getUserId().equals(userId)) {
            throw new AuthException("Unauthorized editor, editor_id mismatch");
        }

        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", articleId));
        article.setIsNews(news);

        article.setUpdateOn(new Date());
        Article updateArticle = this.articleRepository.save(article);
        return this.modelMapper.map(updateArticle, ArticleDto.class);
    }

    @Override
    public ArticleDto updateTagsArticle(Long articleId, Long userId, List<Tag> tags, AuthenticationContext.Principal principal) throws AuthException {

        if (!principal.getUserId().equals(userId)) {
            throw new AuthException("Unauthorized editor, editor_id mismatch");
        }

        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", articleId));

        article.setTags(tags);
        article.setUpdateOn(new Date());
        articleRepository.save(article);
        return this.modelMapper.map(article,ArticleDto.class);
    }

    @Override
    public ArticleDto updateCommentArticle(Long articleId, Long userId, CommentDto commentDto, AuthenticationContext.Principal principal) throws AuthException {

        if (!principal.getUserId().equals(userId)) {
            throw new AuthException("Unauthorized user, user_id mismatch");
        }

        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", articleId));
        Category commentUpdate = this.modelMapper.map(commentDto, Category.class);
        article.setCategory(commentUpdate);
        article.setUpdateOn(new Date());
        articleRepository.save(article);
        return this.modelMapper.map(article,ArticleDto.class);
    }

    @Override
    public ArticleDto updateCategoriesArticle(AuthenticationContext.Principal principal, Long articleId, Long userId, CategoryDto categoryDto) throws AuthException {

        if (!principal.getUserId().equals(userId)) {
            throw new AuthException("Unauthorized editor, editor_id mismatch");
        }

        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", articleId));
        Category categoryUpdate = this.modelMapper.map(categoryDto, Category.class);
        article.setCategory(categoryUpdate);
        article.setUpdateOn(new Date());
        articleRepository.save(article);
        return this.modelMapper.map(article,ArticleDto.class);
    }

    @Override
    public List<ArticleDto> searchPosts(String keyword) {
        List<Article> articles = this.articleRepository.searchByTitle("%"+keyword+"%");
        List<ArticleDto> postDtos = articles.stream().map((post)->this.modelMapper.map(post, ArticleDto.class)).collect(Collectors.toList());
        return postDtos;
    }



}
