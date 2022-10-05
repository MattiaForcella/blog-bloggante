package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.dto.CategoryDto;
import co.develhope.team3.blog.exceptions.ResourceNotFoundException;
import co.develhope.team3.blog.models.Category;
import co.develhope.team3.blog.payloads.response.CategoryResponse;
import co.develhope.team3.blog.repository.CategoryRepository;
import co.develhope.team3.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    /*
    @Autowired
    private UtilsService utilsService;

     */

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
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<Category> pageCategory = this.categoryRepository.findAll(p);

        List<Category> allCategories = pageCategory.getContent();

        List<CategoryDto> categoryDtos = allCategories.stream().map((category) -> this.modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDtos);
        categoryResponse.setPageNumber(pageCategory.getNumber());
        categoryResponse.setPageSize(pageCategory.getSize());
        categoryResponse.setTotalElements(pageCategory.getTotalElements());
        categoryResponse.setTotalPages(pageCategory.getTotalPages());
        categoryResponse.setLastPage(pageCategory.isLast());

        return categoryResponse;
    }
}
