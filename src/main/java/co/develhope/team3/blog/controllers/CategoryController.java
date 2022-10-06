package co.develhope.team3.blog.controllers;


import co.develhope.team3.blog.config.AppConstants;
import co.develhope.team3.blog.models.dto.CategoryDto;
import co.develhope.team3.blog.payloads.response.CategoryDeleteResponse;
import co.develhope.team3.blog.payloads.response.CategoryResponse;
import co.develhope.team3.blog.services.CategoryService;
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
    /*
    @Autowired
    private UtilsService utilsService;

     */

    //@HierarchicalSecurity(bottomRole = "ROLE_EDITOR")
    @PostMapping("/user/{userId}/create-category")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto,
                                                        @PathVariable Long userId) throws AuthException {
/*
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        utilsService.authControl(userId, principal);

 */

        CategoryDto createCategory = this.categoryService.createCategory(categoryDto);

        return  new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

    }

    //@HierarchicalSecurity(bottomRole = "ROLE_ADMIN")
    @PutMapping("/update/user/{userId}/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long userId,
                                                      @PathVariable Long categoryId,
                                                      @Valid @RequestBody CategoryDto categoryDto) throws AuthException {

        /*
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        utilsService.authControl(userId, principal);

         */

        CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);

        return new ResponseEntity<>(updateCategory, HttpStatus.GONE);
    }

    //@PublicEndpoint
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId){

        CategoryDto categoryDto = this.categoryService.getCategory(categoryId);

        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }

    //@HierarchicalSecurity(bottomRole = "ROLE_ADMIN")
    @DeleteMapping("/{catId}")
    public ResponseEntity<CategoryDeleteResponse> deleteCategory(@PathVariable Long categoryId) {
//@TODO authentication
        CategoryDto categoryDeleted = this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<CategoryDeleteResponse>(new CategoryDeleteResponse("category is deleted successfully !!", categoryDeleted),
                HttpStatus.OK);
    }

    //@PublicEndpoint
    @GetMapping("/")
    public ResponseEntity<CategoryResponse> getCategories(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                           @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                           @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                           @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        CategoryResponse categories = this.categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortDir);

        return ResponseEntity.ok(categories);
    }

}
