package be.ehb.petshopboys.repository;

import be.ehb.petshopboys.model.CartItem;
import be.ehb.petshopboys.model.Product;
import be.ehb.petshopboys.model.Category;
import be.ehb.petshopboys.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductAndUser(Product product, User user);
    List<CartItem> findByUser(User user);
}