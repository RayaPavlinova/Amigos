package amigos.tobacco.shop.utils;

import amigos.tobacco.shop.model.CartItem;
import amigos.tobacco.shop.model.Category;
import amigos.tobacco.shop.model.Product;
import amigos.tobacco.shop.model.dto.CartItemDTO;
import amigos.tobacco.shop.model.dto.CategoryDTO;
import amigos.tobacco.shop.model.dto.ProductDTO;

public class RequestBodyToEntityConverter {

    private RequestBodyToEntityConverter() {

    }

    public static Product convertToProduct(final ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setCategories(productDTO.getCategories());

        return product;
    }

    public static ProductDTO convertToProductDTO(final Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId().toString());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setCategories(product.getCategories());

        return productDTO;
    }

    public static CartItemDTO convertToCartItemDTO(CartItem cartItem) {
        CartItemDTO cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setCartId(cartItem.getCart().getId());
        cartItemDTO.setName(cartItem.getName());
        cartItemDTO.setPrice(cartItem.getPrice());
        cartItemDTO.setProductId(cartItem.getProduct().getId());
        cartItemDTO.setQuantity(cartItem.getQuantity());

        return cartItemDTO;
    }

    public static CategoryDTO convertToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());

        return categoryDTO;
    }
}
