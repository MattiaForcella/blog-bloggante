package co.develhope.team3.blog.payloads;

import co.develhope.team3.blog.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthRequest{

    private String token;
    private UserDto user;

}
