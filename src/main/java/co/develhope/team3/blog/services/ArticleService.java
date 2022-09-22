package co.develhope.team3.blog.services;

import co.develhope.team3.blog.dto.ArticleDto;
import co.develhope.team3.blog.dto.CategoryDto;
import co.develhope.team3.blog.dto.CommentDto;
import co.develhope.team3.blog.models.Tag;
import co.develhope.team3.blog.payloads.ArticleResponse;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;

import javax.security.auth.message.AuthException;
import java.util.List;

public interface ArticleService {
    ArticleDto createArticle(ArticleDto articleDto, Long userId, Long categoryId, AuthenticationContext.Principal principal) throws AuthException;

    List<ArticleDto> getArticleByUser(Long id);

    List<ArticleDto> getArticleByCategory(Long id);

    ArticleResponse getAllArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    ArticleDto getArticleById(Long id);

    ArticleDto deleteArticle(Long id, AuthenticationContext.Principal principal, Long userId) throws AuthException;

    ArticleDto updateArticle(ArticleDto articleDto, Long articleId, Long userId, AuthenticationContext.Principal principal) throws AuthException;


    ArticleDto updateTitleArticle(Long articleId, Long userId, AuthenticationContext.Principal principal, String title) throws AuthException;

    ArticleDto updateContentArticle(Long articleId, String content, Long userId, AuthenticationContext.Principal principal) throws AuthException;

    ArticleDto updateNewsArticle(Long articleId, Long userId, Boolean news, AuthenticationContext.Principal principal) throws AuthException;

    ArticleDto updateTagsArticle(Long articleId, Long userId, List<Tag> tags, AuthenticationContext.Principal principal) throws AuthException;

    ArticleDto updateCommentArticle(Long articleId, Long userId, CommentDto commentId, AuthenticationContext.Principal principal) throws AuthException;


    ArticleDto updateCategoriesArticle(AuthenticationContext.Principal principal, Long articleId, Long userId, CategoryDto categoryDto) throws AuthException;

    List<ArticleDto> searchPosts(String keywords);
}
