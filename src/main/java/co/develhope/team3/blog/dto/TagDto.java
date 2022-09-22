package co.develhope.team3.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class TagDto {

    private Long id;

    @NotBlank
    private String name;

    private List<ArticleDto> articles;
}
