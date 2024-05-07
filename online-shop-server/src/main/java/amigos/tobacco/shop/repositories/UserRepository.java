package amigos.tobacco.shop.repositories;

import amigos.tobacco.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(final String username);

    Boolean existsByUsername(final String username);
}
