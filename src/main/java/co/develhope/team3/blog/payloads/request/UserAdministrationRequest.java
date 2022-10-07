package co.develhope.team3.blog.payloads.request;

import co.develhope.team3.blog.models.user.Role;
import lombok.Data;

@Data
public class UserAdministrationRequest {
    private Boolean ban;
    private Role role;
}
