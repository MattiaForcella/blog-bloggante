package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.dto.ArticleDto;
import co.develhope.team3.blog.dto.CategoryDto;
import co.develhope.team3.blog.dto.CommentDto;
import co.develhope.team3.blog.dto.TagDto;
import co.develhope.team3.blog.exceptions.ResourceNotFoundException;
import co.develhope.team3.blog.models.*;
import co.develhope.team3.blog.payloads.ArticleResponse;
import co.develhope.team3.blog.repository.*;
import co.develhope.team3.blog.services.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
    private TagService tagService;
    @Autowired
    CommentService commentService;


    @Override
    public ArticleDto createArticle(ArticleDto articleDto, Long userId, Long categoryId) {
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
    public ArticleDto deleteArticle(Long id) {
        Article article = this.articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article ", "article id", id));
        ArticleDto articleDto = this.modelMapper.map(article, ArticleDto.class);

        this.articleRepository.delete(article);
        return articleDto;
    }

    @Override
    public ArticleDto updateArticle(ArticleDto articleDto, Long articleId) {

        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", articleId));
        article.setIsNews(articleDto.getIsNews());
        article.setImageName(articleDto.getImageName());
        article.setUpdateOn(new Date());

        Article updateArticle = this.articleRepository.save(article);
        return this.modelMapper.map(updateArticle, ArticleDto.class);
    }

    @Override
    public ArticleDto updateTitleArticle(Long id, String title) {
        Article article = this.articleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", id));
        article.setTitle(title);

        article.setUpdateOn(new Date());
        Article updateArticle = this.articleRepository.save(article);
        return this.modelMapper.map(updateArticle,ArticleDto.class);
    }

    @Override
    public ArticleDto updateContentArticle(Long id, String content) {
        Article article = this.articleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", id));
        article.setContent(content);

        article.setUpdateOn(new Date());
        Article updateArticle = this.articleRepository.save(article);
        return this.modelMapper.map(updateArticle, ArticleDto.class);
    }

    @Override
    public ArticleDto updateNewsArticle(Long id, Boolean news) {
        Article article = this.articleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", id));
        article.setIsNews(news);

        article.setUpdateOn(new Date());
        Article updateArticle = this.articleRepository.save(article);
        return this.modelMapper.map(updateArticle, ArticleDto.class);
    }

    @Override
    public ArticleDto updateTagsArticle(Long id, List<Tag> tags) {

        Article article = this.articleRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", id));

        article.setTags(tags);
        article.setUpdateOn(new Date());
        articleRepository.save(article);
        return this.modelMapper.map(article,ArticleDto.class);
    }

    @Override
    public ArticleDto updateCommentArticle(Long articleId, CommentDto commentDto) {
        Article article = this.articleRepository.findById(articleId)
                .orElseThrow(()-> new ResourceNotFoundException("Article", "article id", articleId));
        Category commentUpdate = this.modelMapper.map(commentDto, Category.class);
        article.setCategory(commentUpdate);
        article.setUpdateOn(new Date());
        articleRepository.save(article);
        return this.modelMapper.map(article,ArticleDto.class);
    }

    @Override
    public ArticleDto updateCategoriesArticle(Long articleId, CategoryDto categoryDto) {
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
