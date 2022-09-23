package co.develhope.team3.blog.controllers;


import co.develhope.team3.blog.dto.CategoryDto;
import co.develhope.team3.blog.services.CategoryService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.HierarchicalSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @HierarchicalSecurity(bottomRole = "ROLE_EDITOR")
    @PostMapping("/user/{userId}/create-category")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                        @PathVariable Long userId) throws AuthException {
        AuthenticationContext.Principal principal = AuthenticationContext.get();

        CategoryDto createCategory = this.categoryService.createCategory(categoryDto, principal, userId);

        return  new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

    }

    @HierarchicalSecurity(bottomRole = "ROLE_ADMIN")
    @PutMapping("/update/user/{userId}/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long userId,
                                                      @PathVariable Long categoryId,
                                                      @Valid @RequestBody CategoryDto categoryDto) throws AuthException {
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, userId, categoryId, principal);
        return new ResponseEntity<>(updateCategory, HttpStatus.GONE);
    }

}
