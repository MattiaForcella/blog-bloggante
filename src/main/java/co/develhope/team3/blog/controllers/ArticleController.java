package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.utils.AppConstants;
import co.develhope.team3.blog.models.dto.ArticleDto;
import co.develhope.team3.blog.models.dto.CategoryDto;
import co.develhope.team3.blog.models.dto.CommentDto;
import co.develhope.team3.blog.models.Tag;
import co.develhope.team3.blog.payloads.response.ArticleResponse;
import co.develhope.team3.blog.payloads.response.ArticleDeleteResponse;
import co.develhope.team3.blog.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.message.AuthException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /*
    @Autowired
    private FileService fileService;
    @Autowired
    private UtilsService utilsService;

     */

    @Value("${project.image}")
    private String path;


    //@HierarchicalSecurity(bottomRole = "ROLE_EDITOR")
    @PostMapping("/user/{userId}/category/{categoryId}/articles")
    public ResponseEntity<ArticleDto> createArticle(@RequestBody @Valid ArticleDto articleDto,
                                        @PathVariable Long userId,
                                        @PathVariable Long categoryId) throws AuthException {
/*
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        utilsService.authControl(userId, principal);

 */

        ArticleDto createArticle =this.articleService.createArticle(articleDto,categoryId,userId);

        return new ResponseEntity<ArticleDto>(createArticle, HttpStatus.CREATED);

    }

    //@PublicEndpoint
    @GetMapping("/user/{userId}/articles")
    public ResponseEntity<List<ArticleDto>> getArticleByUser(@PathVariable Long userId){
        return new ResponseEntity<List<ArticleDto>>(articleService.getArticleByUser(userId), HttpStatus.FOUND);
    }

    //@PublicEndpoint
    @GetMapping("/category/{categoryId}/articles")
    public ResponseEntity<List<ArticleDto>> getArticleByCategory(@PathVariable Long categoryId){
        return new ResponseEntity<List<ArticleDto>>(articleService.getArticleByCategory(categoryId), HttpStatus.FOUND);
    }

    //@PublicEndpoint
    @GetMapping("/posts")
    public ResponseEntity<ArticleResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        ArticleResponse postResponse = this.articleService.getAllArticles(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<ArticleResponse>(postResponse, HttpStatus.FOUND);
    }

    //@PublicEndpoint
    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id){
        return new ResponseEntity<ArticleDto>(articleService.getArticleById(id), HttpStatus.FOUND);
    }


    //@HierarchicalSecurity(bottomRole = "ROLE_EDITOR")
    @DeleteMapping("/articles/{userId}/{articleId}")
    public ResponseEntity<ArticleDeleteResponse> deleteArticle(@PathVariable Long articleId, @PathVariable Long userId) throws AuthException {
/*
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        utilsService.authControl(userId, principal);

 */

        ArticleDto articleDeleted = this.articleService.deleteArticle(articleId);

        return new ResponseEntity<ArticleDeleteResponse>( new ArticleDeleteResponse("Article is sucessfully deleted",
                                                                                    articleDeleted),
                                                                                    HttpStatus.OK);
    }

    //@HierarchicalSecurity(bottomRole = "ROLE_EDITOR")
    @PutMapping("/articles/{userId}/{articleId}")
    public ResponseEntity<ArticleDto> updatePost(@RequestBody @Valid ArticleDto articleDto, @PathVariable Long articleId,@PathVariable Long userId) throws AuthException {
/*
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        utilsService.authControl(userId, principal);

 */

        ArticleDto updateArticle = this.articleService.updateArticle(articleDto, articleId);

        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.GONE);
    }

    //@HierarchicalSecurity(bottomRole = "ROLE_EDITOR")
    @PutMapping("/update-title/{userId}/{articleId}/title")
    public ResponseEntity<ArticleDto> updateTitleArticle(@PathVariable Long userId,@PathVariable Long articleId ,@RequestParam("title") String title) throws AuthException {
        /*
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        utilsService.authControl(userId, principal);

         */

        ArticleDto updateArticle = this.articleService.updateTitleArticle(articleId,title);
        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.OK);
    }

    //@HierarchicalSecurity(bottomRole = "ROLE_EDITOR")
    @PutMapping("/update-content/{userId}/{articleId}/content")
    public ResponseEntity<ArticleDto> updateContentArticle(@PathVariable Long userId,@PathVariable Long articleId ,@RequestParam("content") String content) throws AuthException {
/*
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        utilsService.authControl(userId, principal);

 */

        ArticleDto updateArticle = this.articleService.updateContentArticle(articleId, content);

        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.OK);
    }

   // @HierarchicalSecurity(bottomRole = "ROLE_EDITOR")
    @PutMapping("/update-news/{userId}/{articleId}/news")
    public ResponseEntity<ArticleDto> updateNewsArticle(@PathVariable Long userId,@PathVariable Long articleId ,@RequestParam("news") Boolean news) throws AuthException {
/*
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        utilsService.authControl(userId, principal);

 */

        ArticleDto updateArticle = this.articleService.updateNewsArticle(articleId,news);

        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.OK);
    }

    //@HierarchicalSecurity(bottomRole = "ROLE_EDITOR")
    @PutMapping("/update-tag/{userId}/{articleId}")
    public ResponseEntity<ArticleDto> updateTagArticle(@PathVariable Long userId,@PathVariable Long articleId , @RequestBody List<Tag> tags) throws AuthException {
/*
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        utilsService.authControl(userId, principal);

 */

        ArticleDto updateArticle = this.articleService.updateTagsArticle( articleId, tags);

        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.OK);
    }

    //@HierarchicalSecurity(bottomRole = "ROLE_EDITOR")
    @PutMapping("/update-category/{userId}/{articleId}")
    public ResponseEntity<ArticleDto> updateCategoryArticle(@PathVariable Long userId,@PathVariable Long articleId , @RequestBody CategoryDto category) throws AuthException {
/*
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        utilsService.authControl(userId, principal);

 */

        ArticleDto updateArticle = this.articleService.updateCategoriesArticle(articleId, category);

        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.OK);
    }

    //@HierarchicalSecurity(bottomRole = "ROLE_USER")
    @PutMapping("/update-comment/{userId}/{articleId}/{commentId}")
    public ResponseEntity<ArticleDto> updateCommentArticle(@PathVariable Long userId,@PathVariable Long articleId , @RequestBody CommentDto commentId) throws AuthException {
/*
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        utilsService.authControl(userId, principal);

 */

        ArticleDto updateArticle = this.articleService.updateCommentArticle(articleId,commentId);

        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.OK);
    }

    //@PublicEndpoint
    @GetMapping("/articles/search/{keywords}")
    public ResponseEntity<List<ArticleDto>> searchArticleByTitle(@PathVariable("keywords") String keywords) {
        List<ArticleDto> result = this.articleService.searchPosts(keywords);
        return new ResponseEntity<List<ArticleDto>>(result, HttpStatus.OK);
    }


    //@HierarchicalSecurity(bottomRole = "ROLE_EDITOR")
    @PostMapping("/article/image/upload/{userId}/{articleId}")
    public ResponseEntity<ArticleDto> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                      @PathVariable Long articleId,
                                                      @PathVariable Long userId) throws IOException, AuthException {
/*
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        utilsService.authControl(userId, principal);


        ArticleDto articleDto = this.articleService.getArticleById(articleId);
        String fileName = this.fileService.uploadImage(path, image, principal, userId);

        articleDto.setImageName(fileName);


        ArticleDto updateArticle = this.articleService.updateArticle(articleDto, articleId);



        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.OK);

 */
        //@TODO
        return null;

    }

    //@PublicEndpoint
    /*
    @GetMapping(value = "/article/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

     */

    //@PublicEndpoint
    @GetMapping("/home")
    public ResponseEntity<List<ArticleDto>> getNewArticles(){
        return articleService.getAllArticlesByIsNews();
    }

}
