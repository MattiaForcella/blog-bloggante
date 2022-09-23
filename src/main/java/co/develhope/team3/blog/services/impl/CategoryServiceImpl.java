package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.dto.CategoryDto;
import co.develhope.team3.blog.models.Category;
import co.develhope.team3.blog.repository.CategoryRepository;
import co.develhope.team3.blog.services.CategoryService;
import co.develhope.team3.blog.services.UtilsService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.Date;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UtilsService utilsService;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        categoryDto.setCreatedOn(new Date());
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category category1 = this.categoryRepository.save(category);
        return this.modelMapper.map(category1, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        categoryDto.setUpdateOn(new Date());
        Category category = this.modelMapper.map(categoryDto, Category.class);
        CategoryDto categoryDtoUpdate = this.modelMapper.map(category, CategoryDto.class);

        return categoryDtoUpdate;

    }
}
