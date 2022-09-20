package co.develhope.team3.blog.repository;

import co.develhope.team3.blog.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Optional;


@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

        Tag findByName(String name);
}
