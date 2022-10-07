package co.develhope.team3.blog.services.impl;

import co.develhope.team3.blog.exceptions.BlogException;
import co.develhope.team3.blog.models.dto.CategoryDto;
import co.develhope.team3.blog.exceptions.ResourceNotFoundException;
import co.develhope.team3.blog.models.Category;
import co.develhope.team3.blog.models.user.RoleName;
import co.develhope.team3.blog.payloads.response.CategoryResponse;
import co.develhope.team3.blog.repository.CategoryRepository;
import co.develhope.team3.blog.security.models.UserPrincipal;
import co.develhope.team3.blog.services.CategoryService;
import co.develhope.team3.blog.utils.Utilities;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private Utilities utilities;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto, UserPrincipal userPrincipal, Long userId) throws AuthException {
        if (userId.equals(userPrincipal.getId())
                || userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))
                || userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority((RoleName.ROLE_EDITOR.toString())))) {

            categoryDto.setCreatedAt(new Date());
            Category category = this.modelMapper.map(categoryDto, Category.class);
            categoryRepository.save(category);
            return this.modelMapper.map(category, CategoryDto.class);
        }
        throw new AuthException("You don't have permission for create a new category");
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId, Long userId, UserPrincipal userPrincipal) throws AuthException {
        if (userId.equals(userPrincipal.getId())
                || userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {

            categoryDto.setUpdateOn(new Date());
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(()-> new BlogException(HttpStatus.NOT_FOUND, "category not found"));
            category.setTitle(categoryDto.getTitle());
            category.setUpdateOn(new Date());
            category.setDescription(categoryDto.getDescription());
            CategoryDto categoryDtoUpdate = this.modelMapper.map(category, CategoryDto.class);
            categoryRepository.save(category);
            return categoryDtoUpdate;
        }

        throw new AuthException("You don't have permission for update a new category");

    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category cat = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

        return this.modelMapper.map(cat, CategoryDto.class);
    }

    @Override
    public CategoryDto deleteCategory(Long categoryId, UserPrincipal userPrincipal) throws AuthException {

        if (categoryId.equals(userPrincipal.getId()) || userPrincipal.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {

            Category category = this.categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));

            CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);

            this.categoryRepository.delete(category);

            return categoryDto;
        }

        throw new AuthException("You don't have permission for delete a new category");

    }

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        utilities.validatePageNumberAndSize(pageNumber,pageSize);

        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.Direction.DESC, "createdAt");

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
