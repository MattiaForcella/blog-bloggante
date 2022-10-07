package co.develhope.team3.blog.controllers;


import co.develhope.team3.blog.security.models.CurrentUser;
import co.develhope.team3.blog.security.models.UserPrincipal;
import co.develhope.team3.blog.utils.AppConstants;
import co.develhope.team3.blog.models.dto.CategoryDto;
import co.develhope.team3.blog.payloads.response.CategoryDeleteResponse;
import co.develhope.team3.blog.payloads.response.CategoryResponse;
import co.develhope.team3.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    @PostMapping("/user/{userId}/create-category")
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                      @PathVariable Long userId,
                                                      @CurrentUser UserPrincipal userPrincipal) throws AuthException {

        CategoryDto createCategory = this.categoryService.createCategory(categoryDto,userPrincipal, userId);

        return  new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

    }


    @PutMapping("/update/user/{userId}/{categoryId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long userId,
                                                      @PathVariable Long categoryId,
                                                      @Valid @RequestBody CategoryDto categoryDto,
                                                      @CurrentUser UserPrincipal userPrincipal) throws AuthException {

        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId,userId, userPrincipal);

        return new ResponseEntity<>(updateCategory, HttpStatus.GONE);
    }


    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId){

        CategoryDto categoryDto = this.categoryService.getCategory(categoryId);

        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CategoryDeleteResponse> deleteCategory(@PathVariable Long categoryId,
                                                                 @CurrentUser UserPrincipal userPrincipal) throws AuthException {

        CategoryDto categoryDeleted = this.categoryService.deleteCategory(categoryId, userPrincipal);
        return new ResponseEntity<CategoryDeleteResponse>(new CategoryDeleteResponse("category is deleted successfully !!", categoryDeleted),
                HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<CategoryResponse> getCategories(@RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                                                           @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                           @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        CategoryResponse categories = this.categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.ok(categories);
    }

}
