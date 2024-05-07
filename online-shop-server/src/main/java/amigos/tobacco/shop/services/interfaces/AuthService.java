package amigos.tobacco.shop.services.interfaces;

import amigos.tobacco.shop.model.dto.auth.LoginDTO;
import amigos.tobacco.shop.model.dto.auth.RegisterDTO;

public interface AuthService {

    void register(final RegisterDTO registerDTO);

    String login(final LoginDTO loginDTO);
}
