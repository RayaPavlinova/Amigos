package amigos.tobacco.shop.services.interfaces;

import amigos.tobacco.shop.model.User;
import amigos.tobacco.shop.model.dto.auth.UserDTO;
import amigos.tobacco.shop.model.dto.auth.UserInfoDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface for user service.
 */
public interface UserService {

    /**
     * Returns user by id.
     *
     * @param id is the user's id.
     * @return <code>User</code>
     */
    User getById(final Long id);

    /**
     * Returns user by username.
     *
     * @param username is the user's username.
     * @return <code>User</code>
     */
    User getByUsername(final String username);

    /**
     * Get information about a user by username.
     *
     * @param username is the user's username.
     * @return <code>UserInfoDTO</code>
     */
    UserInfoDTO getUserInfo(String username);

    /**
     * Update photo for user.
     *
     * @param photo    is the user's photo.
     * @param username is the user's username.
     */
    void updatePhoto(String username, MultipartFile photo);

    /**
     * Updates user information.
     *
     * @param username is the user's username.
     * @param userDTO  is the user's updated information.
     */
    void updateUser(String username, UserDTO userDTO);
}
