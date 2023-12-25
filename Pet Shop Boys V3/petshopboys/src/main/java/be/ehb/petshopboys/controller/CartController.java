package be.ehb.petshopboys.controller;

import be.ehb.petshopboys.model.Product;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;

public class CartController {

    @PostMapping("/cart")
    public String addToCart(@ModelAttribute("product") Product product, ModelMap map) {
        map.addAttribute("product", product);

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        // Add the session ID to the model
        map.addAttribute("sessionId", sessionId);


        return "cart";
    }
}
