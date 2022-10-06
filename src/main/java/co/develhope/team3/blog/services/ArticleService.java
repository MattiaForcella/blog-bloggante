package co.develhope.team3.blog.services;

import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.dto.ArticleDto;
import co.develhope.team3.blog.models.dto.CategoryDto;
import co.develhope.team3.blog.models.dto.CommentDto;
import co.develhope.team3.blog.models.Tag;
import co.develhope.team3.blog.payloads.response.ArticleResponse;
import co.develhope.team3.blog.payloads.response.PagedResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface ArticleService {
    ArticleDto createArticle(ArticleDto articleDto, Long categoryId, Long userId) ;

    List<ArticleDto> getArticleByUser(Long id);

    List<ArticleDto> getArticleByCategory(Long id);

    ArticleResponse getAllArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    ArticleDto getArticleById(Long id);

    ArticleDto deleteArticle(Long articleId) ;

    ArticleDto updateArticle(ArticleDto articleDto, Long articleId)  ;



    ArticleDto updateTitleArticle(Long articleId, String title);


    ArticleDto updateContentArticle(Long articleId, String content)  ;

    ArticleDto updateNewsArticle(Long articleId, Boolean news)  ;

    ArticleDto updateTagsArticle(Long articleId, List<Tag> tags )  ;

    ArticleDto updateCommentArticle(Long articleId, CommentDto commentId )  ;


    ArticleDto updateCategoriesArticle (Long articleId, CategoryDto categoryDto) ;

    List<ArticleDto> searchPosts(String keywords);

    ResponseEntity<List<ArticleDto>> getAllArticlesByIsNews();

    PagedResponse<Article> getArticlesCreatedBy(String username, Integer page, Integer size);
}
