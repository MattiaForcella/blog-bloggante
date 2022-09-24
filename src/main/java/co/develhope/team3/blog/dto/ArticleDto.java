package co.develhope.team3.blog.dto;

import co.develhope.team3.blog.models.Comment;
import co.develhope.team3.blog.models.Tag;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Max;
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
    @Column(nullable = false, length = 100000000)
    private String content;
    private Date createdOn;
    private Date updateOn;
    private UserDto user;
    private List<Tag> tags= new ArrayList<>();
    private List<Comment> commentDtos = new ArrayList<>();
    private CategoryDto category;
    @NotBlank(message = "è obbligatorio impostare se l'articolo dev'essere tra le news")
    private Boolean isNews;
    private String imageName;

}
