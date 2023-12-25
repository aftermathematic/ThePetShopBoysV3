package be.ehb.petshopboys.dao;

import be.ehb.petshopboys.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDAO extends CrudRepository<Product, Integer> {

    @Query("SELECT p FROM Product p ORDER BY p.id ASC")
    Iterable<Product> findAllOrderByIdAsc();

    @Query("SELECT p FROM Product p ORDER BY p.name ASC")
    Iterable<Product> findAllOrderByNameAsc();


    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    Iterable<Product> findByCategoryId(@Param("categoryId") Integer categoryId);


    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    List<Product> findByNameContaining(@Param("name") String name);

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    /*
    @Query("SELECT p FROM Product p WHERE (:categoryId IS NULL OR p.category_id = :categoryId) ORDER BY p.name ASC")
    Iterable<Product> findAllByCategoryOrderByNameAsc(@Param("categoryId") Long categoryId);
*/

}
