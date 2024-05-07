package amigos.tobacco.shop.model.dto.auth;

import lombok.Data;

import static amigos.tobacco.shop.security.SecurityConstants.JWT_HEADER;

@Data
public class AuthResponseDTO {

    private String accessToken;

    private String tokenType = JWT_HEADER;

    public AuthResponseDTO(final String accessToken) {
        this.accessToken = accessToken;
    }
}
