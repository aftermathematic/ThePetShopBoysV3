package be.ehb.petshopboys.controller;

import be.ehb.petshopboys.dao.CategoryDAO;
import be.ehb.petshopboys.dao.ProductDAO;
import be.ehb.petshopboys.model.Category;
import be.ehb.petshopboys.model.Product;
import be.ehb.petshopboys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class IndexController {

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final UserService userService;

    // Constructor injection
    @Autowired
    public IndexController(ProductDAO productDAO, CategoryDAO categoryDAO, UserService userService) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.userService = userService;
    }

    @GetMapping({"/", "/cat/{id}"})
    public String showProductsByCategory(@PathVariable(required = false) Integer id, Model model) {

        // Check if user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getName())) {
            // If user is not authenticated, redirect to "/login"
            return "redirect:/login";
        }

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

    // ModelAttribute for displaying the categories in the navbar
    @ModelAttribute("allCategories")
    public Iterable<Category> findAllCategories() {
        return categoryDAO.findAllCategoryByIdAsc();
    }

}
