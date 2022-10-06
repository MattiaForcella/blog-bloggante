package co.develhope.team3.blog.payloads.request;

import co.develhope.team3.blog.models.dto.TagDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDeleteRequest {

    String message;
    TagDto tagDto;

}
