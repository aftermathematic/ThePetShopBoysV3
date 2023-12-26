package be.ehb.petshopboys.dao;

import be.ehb.petshopboys.model.CartItem;
import be.ehb.petshopboys.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CartDAO extends CrudRepository<CartItem, Integer> {



}
