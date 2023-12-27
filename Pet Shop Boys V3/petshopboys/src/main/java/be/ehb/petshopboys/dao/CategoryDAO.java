package be.ehb.petshopboys.dao;

import be.ehb.petshopboys.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

// DAO for the Category entity.
// This DAO is used to fetch data from the database and extends the CrudRepository interface
// CrudRepository provides basic CRUD operations (Create, Read, Update, Delete)
public interface CategoryDAO extends CrudRepository<Category, Integer> {
    // Custom query to fetch all categories ordered by ID
    @Query("SELECT c FROM Category c ORDER BY c.id ASC")
    Iterable<Category> findAllCategoryByIdAsc();

    // Custom query to fetch all categories ordered by name
    @Query("SELECT c FROM Category c ORDER BY c.name ASC")
    Iterable<Category> findAllCategoryByNameAsc();

}
