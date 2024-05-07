package amigos.tobacco.shop.services;

import amigos.tobacco.shop.exceptions.CouldntUploadPhotoException;
import amigos.tobacco.shop.model.Cart;
import amigos.tobacco.shop.model.CartItem;
import amigos.tobacco.shop.model.Product;
import amigos.tobacco.shop.model.User;
import amigos.tobacco.shop.model.dto.ProductDTO;
import amigos.tobacco.shop.repositories.CartItemRepository;
import amigos.tobacco.shop.repositories.ProductRepository;
import amigos.tobacco.shop.services.interfaces.ProductService;
import amigos.tobacco.shop.services.interfaces.UserService;
import amigos.tobacco.shop.utils.RequestBodyToEntityConverter;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final UserService userService;

    private final CartItemRepository cartItemRepository;

    public ProductServiceImpl(ProductRepository productRepository, UserService userService, CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Product getProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new EntityNotFoundException("Product couldn't be found!");
        }
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        Product product = this.getProduct(id);

        productRepository.deleteById(product.getId());
    }

    @Override
    public List<Product> getSellerProducts(String username) {
        User user = userService.getByUsername(username);

        Long userId = user.getId();
        return productRepository.getProductsBySellerId(userId);
    }

    @Override
    public void addProductToCart(final Long id, final Integer quantity) {
        Product product = this.getProduct(id);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(userDetails.getUsername());

        Cart cart = user.getCart();

        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setName(product.getName());
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        product.linkCartItem(cartItem);
        productRepository.save(product);
    }

    @Override
    public Long addProduct(ProductDTO productDTO) {
        Product product = RequestBodyToEntityConverter.convertToProduct(productDTO);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userService.getByUsername(userDetails.getUsername());
        product.setUser(user);
        productRepository.save(product);

        return product.getId();
    }

    @Override
    public void updatePhoto(Long id, MultipartFile photo) {
        Product product = this.getProduct(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getByUsername(username);

        if (!product.getUser().getUsername().equals(user.getUsername())) {
            throw new AccessDeniedException("access_denied");
        }

        try {
            product.setPhoto(photo.getBytes());
        } catch (IOException e) {
            throw new CouldntUploadPhotoException("product_photo_not_uploaded");
        }

        productRepository.save(product);
    }

    @Override
    public void updateProduct(Long id, ProductDTO productDTO) {
        Product product = this.getProduct(id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserUsername = auth.getName();
        String productSellerUsername = product.getUser().getUsername();

        if (!currentUserUsername.equals(productSellerUsername)) {
            throw new AccessDeniedException("access_denied");
        }
        product.setQuantity(productDTO.getQuantity());
        product.setName(productDTO.getName());
        product.setCategories(productDTO.getCategories());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());

        productRepository.save(product);
    }
}
