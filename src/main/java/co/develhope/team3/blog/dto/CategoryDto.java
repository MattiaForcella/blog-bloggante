package co.develhope.team3.blog.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    @NotBlank
    @Size(min = 3, message = "min size of category title is 3 chars")
    private String title;

    @NotBlank
    @Size(min = 10, message = "min size of category description is 10 chars")
    private String description;


    private List<ArticleDto> articles=new ArrayList<>();

    private Date createdOn;
    private Date updateOn;
}
