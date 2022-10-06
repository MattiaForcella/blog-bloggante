package co.develhope.team3.blog.models.dto;

import co.develhope.team3.blog.models.Article;
import co.develhope.team3.blog.models.Comment;
import co.develhope.team3.blog.models.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String password;

    private Boolean ban;
    private Boolean isActive;

    private List<Article> articleDtos = new ArrayList<>();
    private List<Comment> commentDtos = new ArrayList<>();
    private List<Role> roles = new ArrayList<>();

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
