package co.develhope.team3.blog.repository;

import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.Category;
import co.develhope.team3.blog.models.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {

    List<Article> findByCategory(Category category);

    @Query("select a from Article a where a.title like :key")
    List<Article> searchByTitle(@Param("key") String title);

    @Query(value = "select a from Article a where a.is_news = 1", nativeQuery = true)
    List<Article> findByIsNews();

    Page<Article> findByCreatedBy(Long id, Pageable pageable);

    List<Article> findByUser(User user);
}

