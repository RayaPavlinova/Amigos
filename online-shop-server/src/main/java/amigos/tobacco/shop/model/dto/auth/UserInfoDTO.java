package amigos.tobacco.shop.model.dto.auth;

import amigos.tobacco.shop.model.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserInfoDTO {

    private Set<Role> roles;

    private String email;
}

