package be.ehb.petshopboys.dao;

import be.ehb.petshopboys.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDAO extends CrudRepository<Category, Integer> {
    @Query("SELECT c FROM Category c ORDER BY c.id ASC")
    Iterable<Category> findAllCategoryByIdAsc();

    @Query("SELECT c FROM Category c ORDER BY c.name ASC")
    Iterable<Category> findAllCategoryByNameAsc();

}
