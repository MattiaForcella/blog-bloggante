package co.develhope.team3.blog.services;

import co.develhope.team3.blog.dto.ArticleDto;
import co.develhope.team3.blog.dto.CategoryDto;
import co.develhope.team3.blog.dto.CommentDto;
import co.develhope.team3.blog.models.Comment;
import co.develhope.team3.blog.models.Tag;
import co.develhope.team3.blog.payloads.ArticleResponse;

import java.util.List;

public interface ArticleService {
    ArticleDto createArticle(ArticleDto articleDto, Long userId, Long categoryId);

    List<ArticleDto> getArticleByUser(Long id);

    List<ArticleDto> getArticleByCategory(Long id);

    ArticleResponse getAllArticles(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    ArticleDto getArticleById(Long id);

    ArticleDto deleteArticle(Long id);

    ArticleDto updateArticle(ArticleDto articleDto, Long articleId);


    ArticleDto updateTitleArticle(Long id, String title);

    ArticleDto updateContentArticle(Long id, String content);

    ArticleDto updateNewsArticle(Long id, Boolean news);

    ArticleDto updateTagsArticle(Long id, List<Tag> tags);

    ArticleDto updateCommentArticle(Long articleId, CommentDto commentId);


    ArticleDto updateCategoriesArticle(Long articleId, CategoryDto categoryDto);

    List<ArticleDto> searchPosts(String keywords);
}
