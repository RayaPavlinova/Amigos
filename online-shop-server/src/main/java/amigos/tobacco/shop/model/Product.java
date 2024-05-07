package amigos.tobacco.shop.model;

import amigos.tobacco.shop.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "products")
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;

    private Double price;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> categories;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @OneToMany(orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    @Lob
    @Column(name = "photo")
    @Nullable
    private byte[] photo;

    public void linkCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }
    private int quantity;
    public void deleteCartItem(CartItem cartItem) {
        this.cartItems.remove(cartItem);
    }
}
