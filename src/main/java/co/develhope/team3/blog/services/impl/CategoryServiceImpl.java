package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.dto.CategoryDto;
import co.develhope.team3.blog.exceptions.ResourceNotFoundException;
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
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category cat = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        return this.modelMapper.map(cat, CategoryDto.class);
    }

    @Override
    public CategoryDto deleteCategory(Long categoryId) {

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category_id",categoryId));

        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);

        this.categoryRepository.delete(category);
        return  categoryDto;

    }

    @Override
    public List<CategoryDto> getCategories() {

        List<Category> categories = this.categoryRepository.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
                .collect(Collectors.toList());

        return categoryDtos;
    }
}
