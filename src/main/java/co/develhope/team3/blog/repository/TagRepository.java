package co.develhope.team3.blog.repository;

import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

        Tag findByName(String name);

        @Query(value = "select t from tags t where t.name like :tag", nativeQuery = true)
        List<Tag> findAllByName(@Param("tag") String name);

        List<Tag> findAllByNameLike(String s);
}
