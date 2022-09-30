package co.develhope.team3.blog.dto;

import co.develhope.team3.blog.models.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
public class CommentDto {

    @NotNull
    private Long id;
    //Ricordarsi di fare le converesioni per il passaggio da e in database

    private Date createdOn;
    @NotBlank(message = "il commento non puo essere vuoto")
    @Size(max = 1000, message = "il commento puo avere al massimo 1000 caratteri")
    private String content;
    private Integer flag;
    private ArticleDto article;
    private UserDto user;

    public Date setCreateOnWithMill (Long mills){
        return this.createdOn = new Date(mills);

    }
}
