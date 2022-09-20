package co.develhope.team3.blog.repository;

import co.develhope.team3.blog.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
