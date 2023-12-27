package be.ehb.petshopboys.dao;

import be.ehb.petshopboys.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// DAO for the Product entity.
// This DAO is used to fetch data from the database and extends the CrudRepository interface
// CrudRepository provides basic CRUD operations (Create, Read, Update, Delete)
public interface ProductDAO extends CrudRepository<Product, Integer> {

    // Custom query to fetch all products ordered by ID
    @Query("SELECT p FROM Product p ORDER BY p.id ASC")
    Iterable<Product> findAllOrderByIdAsc();

    // Custom query to fetch all products ordered by name
    @Query("SELECT p FROM Product p ORDER BY p.name ASC")
    Iterable<Product> findAllOrderByNameAsc();

    // Custom query to fetch all products for a specific category
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    Iterable<Product> findByCategoryId(@Param("categoryId") Integer categoryId);

    // Custom query to fetch all products for a specific category ordered by name
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    List<Product> findByNameContaining(@Param("name") String name);

    // Custom query to fetch all products for a specific category ordered by name
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

}
