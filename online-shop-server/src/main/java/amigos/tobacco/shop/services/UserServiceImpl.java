package amigos.tobacco.shop.services;

import amigos.tobacco.shop.exceptions.CouldntUploadPhotoException;
import amigos.tobacco.shop.model.User;
import amigos.tobacco.shop.model.dto.auth.UserDTO;
import amigos.tobacco.shop.model.dto.auth.UserInfoDTO;
import amigos.tobacco.shop.repositories.UserRepository;
import amigos.tobacco.shop.services.interfaces.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new EntityNotFoundException("User not found.");
        }
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new EntityNotFoundException("username_not_found");
        }
    }

    @Override
    public UserInfoDTO getUserInfo(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);

        User user = userOptional.orElseThrow(() -> new EntityNotFoundException("username_not_found"));
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setRoles(user.getRoles());
        userInfoDTO.setEmail(user.getEmail());

        return userInfoDTO;
    }

    @Override
    public void updatePhoto(String username, MultipartFile photo) {
        User user = this.getByUsername(username);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserUsername = auth.getName();

        if (!user.getUsername().equals(currentUserUsername)) {
            throw new AccessDeniedException("access_denied");
        }

        try {
            user.setPhoto(photo.getBytes());
        } catch (IOException e) {
            throw new CouldntUploadPhotoException("user_photo_not_uploaded");
        }

        userRepository.save(user);
    }

    @Override
    public void updateUser(String username, UserDTO userDTO) {
        User user = this.getByUsername(username);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserUsername = auth.getName();

        if (!user.getUsername().equals(currentUserUsername)) {
            throw new AccessDeniedException("access_denied");
        }

        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
    }
}
