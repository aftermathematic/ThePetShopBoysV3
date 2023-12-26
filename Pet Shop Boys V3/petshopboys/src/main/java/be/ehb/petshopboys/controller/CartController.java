package be.ehb.petshopboys.controller;

import be.ehb.petshopboys.dao.CartDAO;
import be.ehb.petshopboys.dao.CategoryDAO;
import be.ehb.petshopboys.dao.ProductDAO;
import be.ehb.petshopboys.model.*;
import be.ehb.petshopboys.repository.*;
import be.ehb.petshopboys.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;

    private  final CartDAO cartDAO;
    private final UserService userService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    private final CartRepository cartRepository;

    @Autowired
    public CartController(ProductDAO productDAO, CategoryDAO categoryDAO,
                          CartDAO cartDAO, UserService userService,
                          UserRepository userRepository, ProductRepository productRepository,
                          CartRepository cartRepository, OrderRepository orderRepository,
                          OrderItemRepository orderItemRepository) {
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

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Integer productId, ModelMap map) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        //User user = userRepository.findByEmail(email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));


        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("Invalid product Id: " + productId));

        Optional<CartItem> existingCartItem = cartRepository.findByProductAndUser(product, user);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartRepository.save(cartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setUser(user);
            newCartItem.setQuantity(1);
            cartRepository.save(newCartItem);
        }

        return "redirect:/cart";
    }


    @GetMapping("/cart")
    public String displayCart(ModelMap map) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        //User user = userRepository.findByEmail(email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));


        List<CartItem> cartItems = cartRepository.findByUser(user); // This line should now work
        map.addAttribute("cartItems", cartItems);

        BigDecimal totalPrice = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        map.addAttribute("totalPrice", totalPrice);

        return "cart";
    }

    @PostMapping("/cart/place-order")
    @Transactional
    public String placeOrder(ModelMap map) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<CartItem> cartItems = cartRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            // If the cart is empty, redirect to the cart page or show a message
            return "redirect:/cart";
        }

        Order order = new Order(); // Create a new order instance
        order.setUser(user);
        order.setOrderDateTime(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.NEW);

        order = orderRepository.save(order); // Persist order first to generate an ID

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order); // Link orderItems to the persisted order
            orderItemRepository.save(orderItem); // Persist orderItem

            order.addOrderItem(orderItem);
        }

        // Immediately clear the cart after persisting order items
        cartRepository.deleteAll(cartItems);

        // Reload the order with its items if necessary
        Order confirmedOrder = orderRepository.findWithOrderItemsById(order.getId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));


        BigDecimal totalPrice = confirmedOrder.getOrderItems().stream()
                .map(orderItem -> orderItem.getProduct().getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        map.addAttribute("totalPrice", totalPrice);

        map.addAttribute("order", confirmedOrder);  // Add the confirmedOrder with its OrderItems to the model for the confirmation page



        return "confirm";
    }
    @ModelAttribute("allCategories")
    public Iterable<Category> findAllCategories() {
        return categoryDAO.findAllCategoryByNameAsc();
    }

}
