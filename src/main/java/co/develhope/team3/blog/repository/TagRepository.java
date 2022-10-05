package co.develhope.team3.blog.repository;

import co.develhope.team3.blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;



public interface TagRepository extends JpaRepository<Tag,Long> {

        @Query("SELECT t FROM Tag t WHERE t.name = ?1")
        Tag findByName(String name);

        @Query(value = "select t from tags t where t.name like :tag", nativeQuery = true)
        List<Tag> findAllByName(@Param("tag") String name);

}
