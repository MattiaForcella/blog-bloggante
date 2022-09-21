package co.develhope.team3.blog.controllers;

import co.develhope.team3.blog.config.AppConstants;
import co.develhope.team3.blog.dto.ArticleDto;
import co.develhope.team3.blog.dto.CategoryDto;
import co.develhope.team3.blog.dto.CommentDto;
import co.develhope.team3.blog.models.Tag;
import co.develhope.team3.blog.payloads.ArticleResponse;
import co.develhope.team3.blog.payloads.DeleteResponse;
import co.develhope.team3.blog.services.ArticleService;
import co.develhope.team3.blog.services.FileService;
import it.pasqualecavallo.studentsmaterial.authorization_framework.filter.AuthenticationContext;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.HierarchicalSecurity;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.PublicEndpoint;
import it.pasqualecavallo.studentsmaterial.authorization_framework.security.ZeroSecurity;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;





    @HierarchicalSecurity(bottomRole = "ROLE_EDITOR")
    @PostMapping("/user/{userId}/category/{categoryId}/articles")
    public ResponseEntity<ArticleDto> createArticle(@RequestBody @Valid ArticleDto articleDto,
                                        @PathVariable Long userId,
                                        @PathVariable Long categoryId){
        AuthenticationContext.Principal principal = AuthenticationContext.get();
        //TODO confrontare id di principal con quello di userID per verificare identità articolo
        ArticleDto createArticle =this.articleService.createArticle(articleDto,userId,categoryId);
        return new ResponseEntity<ArticleDto>(createArticle, HttpStatus.CREATED);
    }

    @PublicEndpoint
    @GetMapping("/user/{userId}/articles")
    public ResponseEntity<List<ArticleDto>> getArticleByUser(@PathVariable Long userId){
        return new ResponseEntity<List<ArticleDto>>(articleService.getArticleByUser(userId), HttpStatus.FOUND);
    }

    @PublicEndpoint
    @GetMapping("/category/{categoryId}/articles")
    public ResponseEntity<List<ArticleDto>> getArticleByCategory(@PathVariable Long categoryId){
        return new ResponseEntity<List<ArticleDto>>(articleService.getArticleByCategory(categoryId), HttpStatus.FOUND);
    }

    @PublicEndpoint
    @GetMapping("/posts")
    public ResponseEntity<ArticleResponse> getAllPost(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

        ArticleResponse postResponse = this.articleService.getAllArticles(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<ArticleResponse>(postResponse, HttpStatus.FOUND);
    }

    @PublicEndpoint
    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id){
        return new ResponseEntity<ArticleDto>(articleService.getArticleById(id), HttpStatus.FOUND);
    }


    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity<DeleteResponse> deleteArticle(@PathVariable Long articleId){
        ArticleDto articleDto = this.articleService.deleteArticle(articleId);
        DeleteResponse deleteResponse = new DeleteResponse("Article is sucessfully deleted", articleDto);
        return new ResponseEntity<DeleteResponse>(deleteResponse, HttpStatus.GONE);
    }

    @PutMapping("/articles/{articleId}")
    public ResponseEntity<ArticleDto> updatePost(@RequestBody @Valid ArticleDto articleDto, @PathVariable Long articleId) {
        ArticleDto updateArticle = this.articleService.updateArticle(articleDto, articleId);
        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.GONE);
    }

    @PutMapping("/update/{id}/title")
    public ResponseEntity<ArticleDto> updateTitleArticle(@PathVariable Long id ,@RequestParam("title") String title){
        return new ResponseEntity<ArticleDto>(articleService.updateTitleArticle(id,title), HttpStatus.OK);
    }

    @PutMapping("/update/{id}/content")
    public ResponseEntity<ArticleDto> updateContentArticle(@PathVariable Long id ,@RequestParam("content") String content){
        return new ResponseEntity<ArticleDto>(articleService.updateContentArticle(id,content), HttpStatus.OK);
    }

    @PutMapping("/update/{id}/news")
    public ResponseEntity<ArticleDto> updateNewsArticle(@PathVariable Long id ,@RequestParam("news") Boolean news){
        return new ResponseEntity<ArticleDto>(articleService.updateNewsArticle(id,news), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ArticleDto> updateTagArticle(@PathVariable Long id , @RequestBody List<Tag> tags){
        return new ResponseEntity<ArticleDto>(articleService.updateTagsArticle(id,tags), HttpStatus.OK);
    }

    @PutMapping("/update/{articleId}")
    public ResponseEntity<ArticleDto> updateCategoryArticle(@PathVariable Long articleId , @RequestBody CategoryDto category){
        return new ResponseEntity<ArticleDto>(articleService.updateCategoriesArticle(articleId,category), HttpStatus.OK);
    }

    @PutMapping("/update/{articleId}/{commentId}")
    public ResponseEntity<ArticleDto> updateCommentArticle(@PathVariable Long articleId , @RequestBody CommentDto commentId){

        return new ResponseEntity<ArticleDto>(articleService.updateCommentArticle(articleId,commentId), HttpStatus.OK);
    }

    @GetMapping("/articles/search/{keywords}")
    public ResponseEntity<List<ArticleDto>> searchArticleByTitle(@PathVariable("keywords") String keywords) {
        List<ArticleDto> result = this.articleService.searchPosts(keywords);
        return new ResponseEntity<List<ArticleDto>>(result, HttpStatus.OK);
    }

    @PostMapping("/article/image/upload/{articleId}")
    public ResponseEntity<ArticleDto> uploadPostImage(@RequestParam("image") MultipartFile image,
                                                      @PathVariable Long articleId) throws IOException {

        ArticleDto articleDto = this.articleService.getArticleById(articleId);
        String fileName = this.fileService.uploadImage(path, image);
        articleDto.setImageName(fileName);
        ArticleDto updateArticle = this.articleService.updateArticle(articleDto, articleId);
        return new ResponseEntity<ArticleDto>(updateArticle, HttpStatus.OK);

    }

    @GetMapping(value = "/article/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }

}
