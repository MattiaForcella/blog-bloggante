package co.develhope.team3.blog.repository;

import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.Category;
import co.develhope.team3.blog.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ArticleRepository extends JpaRepository<Article,Long> {

    List<Article> findByUser(User user);
    List<Article> findByCategory(Category category);

    @Query("select a from Article a where a.title like :key")
    List<Article> searchByTitle(@Param("key") String title);

    @Query("select * from article where article.is_news = 1")
    List<Article> findByIsNews();
}

