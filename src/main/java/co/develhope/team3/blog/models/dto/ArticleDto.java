package co.develhope.team3.blog.models.dto;

import co.develhope.team3.blog.models.Category;
import co.develhope.team3.blog.models.Comment;
import co.develhope.team3.blog.models.Tag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@NoArgsConstructor
public class ArticleDto {

    private Long id;
    @Size(min=5, message = "il titolo deve contenere al minimo 5 caratteri")
    @NotBlank(message = "il titolo non puo essere vuoto")
    private String title;
    @NotBlank(message = "il contenuto non puo essere vuoto")
    private String content;
    private Date createdOn;
    private Date updateOn;


    private UserDto user;




    private List<Tag> tags= new ArrayList<>();
    private List<Comment> commentDtos = new ArrayList<>();
    private List<Category> category = new ArrayList<>();
    @NotBlank(message = "è obbligatorio impostare se l'articolo dev'essere tra le news")
    private Boolean isNews;
    private String imageName;

    public ArticleDto(String title, String content, List<Tag> tags, Boolean isNews) {
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.isNews = isNews;
    }
}
