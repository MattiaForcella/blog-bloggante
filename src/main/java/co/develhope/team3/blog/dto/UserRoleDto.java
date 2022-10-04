package co.develhope.team3.blog.dto;

import co.develhope.team3.blog.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDto {
    private Long id;
    private String role;

}
