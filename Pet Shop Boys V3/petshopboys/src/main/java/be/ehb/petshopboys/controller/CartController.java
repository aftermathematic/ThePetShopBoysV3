package be.ehb.petshopboys.controller;

import be.ehb.petshopboys.dao.CartDAO;
import be.ehb.petshopboys.dao.CategoryDAO;
import be.ehb.petshopboys.dao.ProductDAO;
import be.ehb.petshopboys.model.*;
import be.ehb.petshopboys.repository.*;
import be.ehb.petshopboys.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final CartDAO cartDAO;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;

    // Constructor injection
    @Autowired
    public CartController(ProductDAO productDAO, CategoryDAO categoryDAO, CartDAO cartDAO, UserService userService, UserRepository userRepository, ProductRepository productRepository, CartRepository cartRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.cartDAO = cartDAO;
        this.userService = userService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    // Endpoint for adding a product to the cart
    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Integer productId, ModelMap map) {

        // Get the currently logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user by email
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Find the product by its ID
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + productId));

        // Find the cart item by product and user. Check if it already exists in the cart
        Optional<CartItem> existingCartItem = cartRepository.findByProductAndUser(product, user);

        // If the cart item already exists, increment the quantity
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartRepository.save(cartItem);
        } else {
            // If the cart item does not exist, create a new one
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setUser(user);
            newCartItem.setQuantity(1);
            cartRepository.save(newCartItem);
        }

        return "redirect:/cart";
    }

    // Endpoint for adding a product to the cart
    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam("productId") Integer productId, ModelMap map) {

        // Get the currently logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user by email
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Find the product by its ID
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + productId));

        // Find the cart item by product and user. Check if it already exists in the cart
        Optional<CartItem> existingCartItem = cartRepository.findByProductAndUser(product, user);

        // If the cart item exists, remove it
        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartRepository.delete(cartItem);
        }

        return "redirect:/cart";
    }

    // Endpoint for displaying the cart
    @GetMapping("/cart")
    public String displayCart(ModelMap map) {
        // Get the currently logged in user which is stored in the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user by email
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Find all cart items for the user
        List<CartItem> cartItems = cartRepository.findByUser(user);

        // Add the cart items to the model
        map.addAttribute("cartItems", cartItems);

        // Calculate the total price of all cart items
        BigDecimal totalPrice = cartItems.stream().map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        map.addAttribute("totalPrice", totalPrice);

        return "cart";
    }

    @PostMapping("/cart")
    public String displayCart2(ModelMap map) {
        // Get the currently logged in user which is stored in the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user by email
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Find all cart items for the user
        List<CartItem> cartItems = cartRepository.findByUser(user);

        // Add the cart items to the model
        map.addAttribute("cartItems", cartItems);

        // Calculate the total price of all cart items
        BigDecimal totalPrice = cartItems.stream().map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        map.addAttribute("totalPrice", totalPrice);

        return "cart";
    }

    // Endpoint for placing an order
    @PostMapping("/cart/place-order")
    // Use @Transactional to make sure the entire method is executed in a single transaction.
    // If an exception occurs, the transaction will be rolled back and the database will be in a consistent state.
    @Transactional
    public String placeOrder(ModelMap map) {
        // Get the currently logged in user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Find the user by email
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Find all cart items for the user
        List<CartItem> cartItems = cartRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            // If the cart is empty, redirect to the cart page or show a message
            return "redirect:/cart";
        }

        // Create a new order
        Order order = new Order();
        order.setUser(user);
        order.setOrderDateTime(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.NEW);

        // Save the order
        order = orderRepository.save(order);

        // Create order items for each cart item
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order); // Link orderItems to the persisted order
            orderItemRepository.save(orderItem); // Persist orderItem

            order.addOrderItem(orderItem);
        }

        // Immediately clear the cart after saving order items
        cartRepository.deleteAll(cartItems);

        // Reload the order with its items if necessary
        Order confirmedOrder = orderRepository.findWithOrderItemsById(order.getId()).orElseThrow(() -> new EntityNotFoundException("Order not found"));

        // Calculate the total price of the order
        BigDecimal totalPrice = confirmedOrder.getOrderItems().stream().map(orderItem -> orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);

        // Add the total price to the model
        map.addAttribute("totalPrice", totalPrice);
        map.addAttribute("order", confirmedOrder);  // Add the confirmedOrder with its OrderItems to the model for the confirmation page

        return "confirm";
    }

    // Model attribute for all categories. This is used to display the categories.
    @ModelAttribute("allCategories")
    public Iterable<Category> findAllCategories() {
        return categoryDAO.findAllCategoryByNameAsc();
    }

}
