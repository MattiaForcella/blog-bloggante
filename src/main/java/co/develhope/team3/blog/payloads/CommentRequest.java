package co.develhope.team3.blog.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CommentRequest {
    private Long articleId;
    // Data ricavabile in locale, non ha senso passarlo come parametro
    //private Date createdOn;
    @NotBlank(message = "il commento non puo essere vuoto")
    @Size(max = 1000, message = "il commento puo avere al massimo 1000 caratteri")
    private String content;
}

