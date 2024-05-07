package amigos.tobacco.shop.services;

import amigos.tobacco.shop.exceptions.UserDoesntExistException;
import amigos.tobacco.shop.exceptions.UsernameAlreadyTakenException;
import amigos.tobacco.shop.model.Cart;
import amigos.tobacco.shop.model.Role;
import amigos.tobacco.shop.model.User;
import amigos.tobacco.shop.model.dto.auth.LoginDTO;
import amigos.tobacco.shop.model.dto.auth.RegisterDTO;
import amigos.tobacco.shop.repositories.RoleRepository;
import amigos.tobacco.shop.repositories.UserRepository;
import amigos.tobacco.shop.security.JwtProvider;
import amigos.tobacco.shop.services.interfaces.AuthService;
import amigos.tobacco.shop.services.interfaces.CartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtProvider jwtProvider;

    private final CartService cartService;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, CartService cartService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.cartService = cartService;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) { // NOSONAR - The user repository cannot be null.
            throw new UsernameAlreadyTakenException("username_taken");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Optional<Role> roleOptional = roleRepository.findByName(registerDTO.getRole().name());


        if (roleOptional.isPresent()) {
            Role role = roleOptional.get();
            user.setRoles(new HashSet<>(List.of(role)));
        } else {
            throw new EntityNotFoundException("role_not_found");
        }

        Cart cart = new Cart();
        cartService.save(cart);
        userRepository.save(user);

        user.setCart(cart);
        userRepository.save(user);

        cart.setUser(user);
        cartService.save(cart);
    }

    @Override
    public String login(LoginDTO loginDTO) {
        if (!userRepository.existsByUsername(loginDTO.getUsername())) {
            throw new UserDoesntExistException("username_not_found");
        }

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtProvider.generateToken(authentication);
    }
}
