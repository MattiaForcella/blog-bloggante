package co.develhope.team3.blog.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TagDto {

    private Long id;

    @Size(min = 15, message = "il tag deve contenere massimo 15 caratteri")
    @NotBlank(message = "ogni articolo deve essere associato almeno ad un tag")
    private String name;

    @NotBlank(message = "ogni articolo può avere massimo 5 tag")
    private List<ArticleDto> articles;
}

