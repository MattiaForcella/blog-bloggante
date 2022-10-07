package co.develhope.team3.blog.services;

import co.develhope.team3.blog.models.dto.CategoryDto;
import co.develhope.team3.blog.payloads.response.CategoryResponse;
import co.develhope.team3.blog.security.models.UserPrincipal;

import javax.security.auth.message.AuthException;


public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto, UserPrincipal userPrincipal, Long userId) throws AuthException;

    CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId, Long userId, UserPrincipal userPrincipal) throws AuthException;

    CategoryDto getCategory(Long categoryId);

    CategoryDto deleteCategory(Long categoryId, UserPrincipal userPrincipal) throws AuthException;

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
