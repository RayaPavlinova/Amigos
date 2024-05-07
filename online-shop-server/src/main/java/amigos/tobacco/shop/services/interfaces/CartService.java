package amigos.tobacco.shop.services.interfaces;

import amigos.tobacco.shop.model.Cart;

import java.util.Optional;

public interface CartService {

    Optional<Cart> getCart(final Long id);

    void save(final Cart cart);

}
