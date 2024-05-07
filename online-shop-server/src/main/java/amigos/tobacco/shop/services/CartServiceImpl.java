package amigos.tobacco.shop.services;

import amigos.tobacco.shop.model.Cart;
import amigos.tobacco.shop.repositories.CartRepository;
import amigos.tobacco.shop.services.interfaces.CartService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    public CartServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public Optional<Cart> getCart(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }
}
