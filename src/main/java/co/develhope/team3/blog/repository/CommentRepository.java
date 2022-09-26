package co.develhope.team3.blog.repository;

import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {


    Page<Comment> findAllByArticleId(Article article, Pageable p);

    @Query(value = "SELECT * FROM comments WHERE flag > 0",
            countQuery = "SELECT count(*) FROM comments WHERE flag > 0 ",
            nativeQuery = true)
    Page<Comment> findAllFlaggedComments(Pageable p);
}
