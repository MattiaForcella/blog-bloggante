package co.develhope.team3.blog.payloads.request;

import co.develhope.team3.blog.dto.TagDto;
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
