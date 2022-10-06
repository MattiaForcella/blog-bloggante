package co.develhope.team3.blog.payloads.response;

import co.develhope.team3.blog.models.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDeleteResponse {
    private String message;
    private CategoryDto categoryDto;
}
