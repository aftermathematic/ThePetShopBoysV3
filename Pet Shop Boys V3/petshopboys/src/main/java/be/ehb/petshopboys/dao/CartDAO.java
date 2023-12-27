package be.ehb.petshopboys.dao;

import be.ehb.petshopboys.model.CartItem;
import be.ehb.petshopboys.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

// DAO for the CartItem entity.
// This DAO is used to fetch data from the database and  extends the CrudRepository interface
// CrudRepository provides basic CRUD operations (Create, Read, Update, Delete)
// No other methods are needed, because we don't need to update or delete CartItems
public interface CartDAO extends CrudRepository<CartItem, Integer> {


}
