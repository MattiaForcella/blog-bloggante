package co.develhope.team3.blog.models.dto;

import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.Comment;
import co.develhope.team3.blog.models.user.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty
    @Size(min = 3, max = 20, message = "il campo username deve essere compreso tra i 3 e i 20 caratteri")
    private String username;

    @Email(message = "email non corretta")
    @NotEmpty(message = "il campo email non puo essere vuoto")
    private String email;

    @Size(max = 500)
    private String about;

    @Size(max = 150)
    @JsonBackReference
    private String password;

    private Boolean ban;
    private Boolean isActive;


    private List<Article> articleDtos = new ArrayList<>();
    @JsonIgnore
    private List<Comment> commentDtos = new ArrayList<>();
    @JsonIgnore
    private List<Role> roles = new ArrayList<>();


    public UserDto(Long id, String username, String email, String about, String password, Boolean ban, Boolean isActive, List<Article> articleDtos, List<Comment> commentDtos, List<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.about = about;
        this.password = password;
        this.ban = ban;
        this.isActive = isActive;
        this.articleDtos = articleDtos;
        this.commentDtos = commentDtos;
        this.roles = roles;
    }

    public UserDto(Long id, Boolean activeUser, String about, String email, String username,
                   Boolean ban, List<Article> articles) {

        this.id = id;
        this.isActive= activeUser;
        this.about = about;
        this.email = email;
        this.username=username;

        this.ban = ban;
        this.articleDtos = articles;

    }
}
