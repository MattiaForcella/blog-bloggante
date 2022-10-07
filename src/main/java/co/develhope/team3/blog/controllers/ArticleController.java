package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.models.dto.ArticleContentDto;
import co.develhope.team3.blog.payloads.request.CategoryIdRequest;
import co.develhope.team3.blog.security.models.CurrentUser;
import co.develhope.team3.blog.security.models.UserPrincipal;
import co.develhope.team3.blog.services.CategoryService;
import co.develhope.team3.blog.services.FileService;
import co.develhope.team3.blog.utils.AppConstants;
import co.develhope.team3.blog.models.dto.ArticleDto;
import co.develhope.team3.blog.models.dto.CategoryDto;
import co.develhope.team3.blog.models.dto.CommentDto;
import co.develhope.team3.blog.payloads.response.ArticleResponse;
import co.develhope.team3.blog.payloads.response.ArticleDeleteResponse;
import co.develhope.team3.blog.services.ArticleService;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CategoryService categoryService;



    @Value("${project.image}")
    private String path;



    @PostMapping("/user/{userId}/category/{categoryId}/articles")
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleDto> createArticle(@RequestBody @Valid ArticleDto articleDto,
                                        @PathVariable Long userId,
                                        @PathVariable Long categoryId,
                                        @CurrentUser UserPrincipal currentUser) throws AuthException {

        ArticleDto createArticle = this.articleService.createArticle(articleDto,categoryId,userId, currentUser);

        return new ResponseEntity<ArticleDto>(createArticle, HttpStatus.CREATED);

    }

    @GetMapping("/user/{userId}/articles")
    public ResponseEntity<List<ArticleDto>> getArticlesByUser(@PathVariable Long userId){
        return new ResponseEntity<List<ArticleDto>>(articleService.getArticleByUser(userId), HttpStatus.FOUND);
    }

    @GetMapping("/category/{categoryId}/articles")
    public ResponseEntity<List<ArticleDto>> getArticlesByCategory(@PathVariable Long categoryId){
        return new ResponseEntity<List<ArticleDto>>(articleService.getArticleByCategory(categoryId), HttpStatus.FOUND);
    }

    @GetMapping("/article")
    public ResponseEntity<ArticleResponse> getAllArticle(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) Integer pageSize) {

        ArticleResponse postResponse = this.articleService.getAllArticles(pageNumber, pageSize);
        return new ResponseEntity<ArticleResponse>(postResponse, HttpStatus.FOUND);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id){
        return new ResponseEntity<ArticleDto>(articleService.getArticleById(id), HttpStatus.FOUND);
    }



    @DeleteMapping("/articles/{userId}/{articleId}")
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleDeleteResponse> deleteArticle(@PathVariable Long articleId,
                                                               @PathVariable Long userId,
                                                               @CurrentUser UserPrincipal currentUser) throws AuthException, AccessDeniedException {

        ArticleDto articleDeleted = this.articleService.deleteArticle(articleId, currentUser);

        return new ResponseEntity<ArticleDeleteResponse>( new ArticleDeleteResponse("Article is sucessfully deleted",
                                                                                    articleDeleted),
                                                                                    HttpStatus.OK);
    }


    @PutMapping("/articles/{userId}/{categoryId}/{articleId}")
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleDto> updateArticle(@RequestBody @Valid ArticleDto articleDto,
                                                 @PathVariable Long articleId,
                                                 @PathVariable Long userId,
                                                 @PathVariable Long categoryId,
                                                 @CurrentUser UserPrincipal currentUser) throws AuthException, AccessDeniedException {

        ArticleDto updateArticle = this.articleService.updateArticle(articleDto, articleId, currentUser, categoryId);

        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.GONE);
    }


    @PutMapping("/update-content/{userId}/{articleId}/content")
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleDto> updateContentArticle(@PathVariable Long userId,
                                                           @PathVariable Long articleId,
                                                           @RequestBody ArticleContentDto content,
                                                           @CurrentUser UserPrincipal userPrincipal) throws AuthException {
        String contentString = content.getContent();
        ArticleDto updateArticle = this.articleService.updateContentArticle(articleId, contentString, userPrincipal);

        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.OK);
    }



    @PutMapping("/update-category/{userId}/{articleId}")
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleDto> updateCategoryArticle(@PathVariable Long userId,
                                                            @PathVariable Long articleId ,
                                                            @RequestBody CategoryIdRequest categoryId,
                                                            @CurrentUser UserPrincipal principal) throws AuthException {

        CategoryDto category = categoryService.getCategory(categoryId.getId());

        ArticleDto updateArticle = this.articleService.updateCategoriesArticle(articleId, category,principal);

        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.OK);
    }



    @PutMapping("/update-comment/{userId}/{articleId}/{commentId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleDto> updateCommentArticle(@PathVariable Long userId,
                                                           @PathVariable Long articleId ,
                                                           @RequestBody CommentDto commentId,
                                                           @CurrentUser UserPrincipal userPrincipal) throws AuthException {
            //TODO DA TESTARE!!!
        ArticleDto updateArticle = this.articleService.updateCommentArticle(articleId,commentId, userPrincipal);

        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.OK);
    }


    @GetMapping("/articles/search/{keywords}")
    public ResponseEntity<List<ArticleDto>> searchArticleByTitle(@PathVariable("keywords") String keywords) {
        List<ArticleDto> result = this.articleService.searchPosts( keywords );
        return new ResponseEntity<List<ArticleDto>>(result, HttpStatus.OK);
    }



    @PostMapping("/article/image/upload/{userId}/{categoryId}/{articleId}")
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ArticleDto> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                      @PathVariable Long articleId,
                                                      @PathVariable Long userId,
                                                      @PathVariable Long categoryId,
                                                      @CurrentUser UserPrincipal userPrincipal) throws IOException, AuthException {

        // @TODO DA TESTARE!!!
        ArticleDto articleDto = this.articleService.getArticleById(articleId);

        String fileName = this.fileService.uploadImage(path, image, userPrincipal, userId);

        articleDto.setImageName(fileName);


        ArticleDto updateArticle = this.articleService.updateArticle(articleDto, articleId,userPrincipal,categoryId);

        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.OK);

    }


    @GetMapping(value = "/article/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {

        // @TODO DA TESTARE!!!
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

    @GetMapping("/home")
    public ResponseEntity<List<ArticleDto>> getNewArticles(){
        return articleService.getAllArticlesByIsNews();
    }

}
