package co.develhope.team3.blog.services;

import co.develhope.team3.blog.dto.CategoryDto;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;

import javax.security.auth.message.AuthException;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto, AuthenticationContext.Principal principal, Long userId) throws AuthException;

    CategoryDto updateCategory(CategoryDto categoryDto, Long userId, Long categoryId, AuthenticationContext.Principal principal) throws AuthException;
}
