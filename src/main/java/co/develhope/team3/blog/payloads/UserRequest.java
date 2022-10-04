package co.develhope.team3.blog.payloads;

import lombok.*;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @Size(max = 500)
    private String about;
}
