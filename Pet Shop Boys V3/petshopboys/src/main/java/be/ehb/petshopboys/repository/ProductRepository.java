package be.ehb.petshopboys.repository;

import be.ehb.petshopboys.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// This is the repository for the products. It extends JpaRepository, which contains methods for basic CRUD operations.
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByOrderByIdAsc();
}
