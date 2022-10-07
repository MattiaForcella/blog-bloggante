package co.develhope.team3.blog.services;

import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.dto.ArticleDto;
import co.develhope.team3.blog.models.dto.CategoryDto;
import co.develhope.team3.blog.models.dto.CommentDto;
import co.develhope.team3.blog.payloads.response.ArticleResponse;
import co.develhope.team3.blog.payloads.response.PagedResponse;
import co.develhope.team3.blog.security.models.UserPrincipal;
import org.springframework.http.ResponseEntity;

import javax.security.auth.message.AuthException;
import java.nio.file.AccessDeniedException;
import java.util.List;


public interface ArticleService {
    ArticleDto createArticle(ArticleDto articleDto, Long categoryId, Long userId, UserPrincipal currentUser) ;

    List<ArticleDto> getArticleByUser(Long id);

    List<ArticleDto> getArticleByCategory(Long id);

    ArticleResponse getAllArticles(Integer pageNumber, Integer pageSize);

    ArticleDto getArticleById(Long id);

    ArticleDto deleteArticle(Long articleId, UserPrincipal currentUser) throws AccessDeniedException;

    ArticleDto updateArticle(ArticleDto articleDto, Long articleId, UserPrincipal currentUser, Long categoryId) throws AccessDeniedException;






    ArticleDto updateContentArticle(Long articleId, String content, UserPrincipal userPrincipal) throws AuthException;



    ArticleDto updateCommentArticle(Long articleId, CommentDto commentId, UserPrincipal userPrincipal) throws AuthException;


    ArticleDto updateCategoriesArticle (Long articleId, CategoryDto categoryDto, UserPrincipal principal) throws AuthException;

    List<ArticleDto> searchPosts(String keywords);

    ResponseEntity<List<ArticleDto>> getAllArticlesByIsNews();

    PagedResponse<Article> getArticlesCreatedBy(String username, Integer page, Integer size);
}
