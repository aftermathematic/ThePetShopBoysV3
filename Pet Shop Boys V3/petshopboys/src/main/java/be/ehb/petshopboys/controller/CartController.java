package be.ehb.petshopboys.controller;

import be.ehb.petshopboys.dao.CategoryDAO;
import be.ehb.petshopboys.dao.ProductDAO;
import be.ehb.petshopboys.model.Category;
import be.ehb.petshopboys.model.Product;
import be.ehb.petshopboys.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;

@Controller
public class CartController {

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final UserService userService;


    @Autowired
    public CartController(ProductDAO productDAO, CategoryDAO categoryDAO, UserService userService) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.userService = userService;
    }

    @PostMapping("/cart")
    public String addToCart(@ModelAttribute("product") Product product, ModelMap map) {
        map.addAttribute("product", product);

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        // Add the session ID to the model
        map.addAttribute("sessionId", sessionId);
        return "cart";
    }


    @GetMapping(value={"/cart"})
    public String displayCart() {
        return "cart";
    }

    @ModelAttribute("allCategories")
    public Iterable<Category> findAllCategories() {
        return categoryDAO.findAllCategoryByNameAsc();
    }

}
