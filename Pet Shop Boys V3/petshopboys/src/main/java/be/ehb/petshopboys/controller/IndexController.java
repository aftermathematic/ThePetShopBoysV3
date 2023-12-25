package be.ehb.petshopboys.controller;

import be.ehb.petshopboys.dao.CategoryDAO;
import be.ehb.petshopboys.dao.ProductDAO;
import be.ehb.petshopboys.model.Category;
import be.ehb.petshopboys.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;


@Controller
public class IndexController {

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    @Autowired
    public IndexController(ProductDAO productDAO, CategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
    }

    @GetMapping({"/", "/cat/{id}"})
    public String showProductsByCategory(@PathVariable(required = false) Integer id, Model model) {
        Iterable<Product> products;
        if (id != null) {
            // If a category ID is provided, fetch products for that category
            products = productDAO.findByCategoryId(id);
        } else {
            // If no category ID is provided, fetch all products
            products = productDAO.findAllOrderByNameAsc();
        }
        model.addAttribute("products", products);
        return "index";
    }

    @PostMapping("/cart")
    public String addToCart(@ModelAttribute("product") Product product, ModelMap map) {
        map.addAttribute("product", product);

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        // Add the session ID to the model
        map.addAttribute("sessionId", sessionId);


        return "cart";
    }


    @ModelAttribute("allCategories")
    public Iterable<Category> findAllCategories() {
        return categoryDAO.findAllCategoryByNameAsc();
    }


    @GetMapping(value = {"/cart"})
    public String showCart(ModelMap map) {
        return "cart";
    }



}
