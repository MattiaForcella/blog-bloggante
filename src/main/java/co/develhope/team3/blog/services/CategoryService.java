package co.develhope.team3.blog.services;

import co.develhope.team3.blog.dto.CategoryDto;
import co.develhope.team3.blog.payloads.CategoryResponse;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;

import javax.security.auth.message.AuthException;
import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto) ;

    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) ;

    CategoryDto getCategory(Long categoryId);

    CategoryDto deleteCategory(Long categoryId);

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
