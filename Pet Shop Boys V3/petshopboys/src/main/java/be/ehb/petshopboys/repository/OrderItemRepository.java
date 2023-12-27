package be.ehb.petshopboys.repository;

import be.ehb.petshopboys.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// This is the repository for the order items. It extends JpaRepository, which contains methods for basic CRUD operations.
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
