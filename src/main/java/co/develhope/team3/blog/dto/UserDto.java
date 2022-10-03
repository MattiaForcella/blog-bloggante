package co.develhope.team3.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private List<ArticleDto> articleDtos = new ArrayList<>();
    private List<CommentDto> commentDtos = new ArrayList<>();
    private List<UserRoleDto> roles = new ArrayList<>();

}
