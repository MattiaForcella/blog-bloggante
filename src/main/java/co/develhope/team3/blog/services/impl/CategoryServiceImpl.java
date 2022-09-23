package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.dto.CategoryDto;
import co.develhope.team3.blog.models.Category;
import co.develhope.team3.blog.repository.CategoryRepository;
import co.develhope.team3.blog.services.CategoryService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto, AuthenticationContext.Principal principal, Long userId) throws AuthException {

        if (!principal.getUserId().equals(userId)) {
            throw new AuthException("Unauthorized user, user_id mismatch");
        }

        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category category1 = this.categoryRepository.save(category);
        return this.modelMapper.map(category1, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long userId, Long categoryId, AuthenticationContext.Principal principal) throws AuthException {

        if (!principal.getUserId().equals(userId)) {
            throw new AuthException("Unauthorized user, user_id mismatch");
        }
        Category category = this.modelMapper.map(categoryDto, Category.class);
        CategoryDto categoryDtoUpdate = this.modelMapper.map(category, CategoryDto.class);
        return categoryDtoUpdate;




    }
}
