package be.ehb.petshopboys.config;

import be.ehb.petshopboys.model.Category;
import be.ehb.petshopboys.model.Product;
import be.ehb.petshopboys.model.User;
import be.ehb.petshopboys.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        clearDatabase();
        loadUserData();
        loadCategoryData();
        loadProductData();
    }

    private void clearDatabase() {
        orderItemRepository.deleteAllInBatch();  // Delete all OrderItem entities first
        cartRepository.deleteAllInBatch();       // Then all CartItem entities
        orderRepository.deleteAllInBatch();      // Then Order entities
        productRepository.deleteAllInBatch();    // Now that OrderItems and CartItems are gone, Products can be deleted
        categoryRepository.deleteAllInBatch();   // Delete Categories (if not referenced by Products)
        userRepository.deleteAllInBatch();       // Finally, delete Users (if not referenced by Orders or CartItems)
    }

    private void loadUserData() {
        // Check if the user already exists before saving the new user
        if (!userRepository.findByEmail("admin@petshopboys.be").isPresent()) {
            User user1 = new User();
            user1.setEmail("admin@petshopboys.be");
            user1.setPassword(passwordEncoder.encode("123456789"));
            userRepository.save(user1);
        }

        if (!userRepository.findByEmail("jan.vermeerbergen@student.ehb.be").isPresent()) {
            User user2 = new User();
            user2.setEmail("jan.vermeerbergen@student.ehb.be");
            user2.setPassword(passwordEncoder.encode("123456789"));
            userRepository.save(user2);
        }
    }

    private void loadCategoryData() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Tropical Fish");
        category1.setDescription("Category for tropical fish");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Aquariums");
        category2.setDescription("Category for aquariums");
        categoryRepository.save(category2);

        Category category3 = new Category();
        category3.setId(3L);
        category3.setName("Fish Food");
        category3.setDescription("Category for fish food");
        categoryRepository.save(category3);

        Category category4 = new Category();
        category4.setId(4L);
        category4.setName("Other sea creatures");
        category4.setDescription("Category for other types of sea creatures");
        categoryRepository.save(category4);
    }


    private void loadProductData() {

        Category category1 = categoryRepository.findByName("Tropical Fish").orElseThrow();
        Category category2 = categoryRepository.findByName("Aquariums").orElseThrow();
        Category category3 = categoryRepository.findByName("Fish Food").orElseThrow();
        Category category4 = categoryRepository.findByName("Other sea creatures").orElseThrow();

        // Dummy description
        String description = "lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor " + "incididunt ut labore et dolore magna aliqua";

        // Tropical Fish
        Product product1a = new Product();
        product1a.setName("Happy fish");
        product1a.setDescription(description);
        product1a.setPrice(BigDecimal.valueOf(50.00));
        product1a.setImage("https://i.ibb.co/6Pfhg6M/fish1.png");
        product1a.setCategory(category1);
        productRepository.save(product1a);

        Product product1b = new Product();
        product1b.setName("Happier fish");
        product1b.setDescription(description);
        product1b.setPrice(BigDecimal.valueOf(50.00));
        product1b.setImage("https://i.ibb.co/WDH68F8/fish2.png");
        product1b.setCategory(category1);
        productRepository.save(product1b);

        Product product1c = new Product();
        product1c.setName("Mean fish");
        product1c.setDescription(description);
        product1c.setPrice(BigDecimal.valueOf(50.00));
        product1c.setImage("https://i.ibb.co/1MngGxQ/fish3.png");
        product1c.setCategory(category1);
        productRepository.save(product1c);

        Product product1d = new Product();
        product1d.setName("Radioactive Clown Fish");
        product1d.setDescription(description);
        product1d.setPrice(BigDecimal.valueOf(50.00));
        product1d.setImage("https://i.ibb.co/crV3TZq/fish4.png");
        product1d.setCategory(category1);
        productRepository.save(product1d);

        Product product1e = new Product();
        product1e.setName("Danger Fish");
        product1e.setDescription(description);
        product1e.setPrice(BigDecimal.valueOf(50.00));
        product1e.setImage("https://i.ibb.co/LkHpY45/fish5.png");
        product1e.setCategory(category1);
        productRepository.save(product1e);

        Product product1f = new Product();
        product1f.setName("Angry Puffer Fish");
        product1f.setDescription(description);
        product1f.setPrice(BigDecimal.valueOf(50.00));
        product1f.setImage("https://i.ibb.co/wJrMH58/fish6.png");
        product1f.setCategory(category1);
        productRepository.save(product1f);

        // Aquariums
        Product product2a = new Product();
        product2a.setName("Fish Bowl");
        product2a.setDescription(description);
        product2a.setPrice(BigDecimal.valueOf(35.00));
        product2a.setImage("https://i.ibb.co/B31qrr7/aqarium1.png");
        product2a.setCategory(category2);
        productRepository.save(product2a);

        Product product2b = new Product();
        product2b.setName("Fish Cube");
        product2b.setDescription(description);
        product2b.setPrice(BigDecimal.valueOf(35.00));
        product2b.setImage("https://i.ibb.co/Vg8ywc7/aquarium2.png");
        product2b.setCategory(category2);
        productRepository.save(product2b);

        Product product2c = new Product();
        product2c.setName("Horror Fish Tank");
        product2c.setDescription(description);
        product2c.setPrice(BigDecimal.valueOf(30.00));
        product2c.setImage("https://i.ibb.co/2Z4pLwT/aquarium3.png");
        product2c.setCategory(category2);
        productRepository.save(product2c);

        Product product2e = new Product();
        product2e.setName("Big Aquarium for Small Fish");
        product2e.setDescription(description);
        product2e.setPrice(BigDecimal.valueOf(35.00));
        product2e.setImage("https://i.ibb.co/s2654N4/aquarium4.png");
        product2e.setCategory(category2);
        productRepository.save(product2e);

        Product product2d = new Product();
        product2d.setName("Small Aquarium for Big Fish");
        product2d.setDescription(description);
        product2d.setPrice(BigDecimal.valueOf(85.00));
        product2d.setImage("https://i.ibb.co/qykJJZp/aquarium5.png");
        product2d.setCategory(category2);
        productRepository.save(product2d);

        // Fish Food
        Product product3a = new Product();
        product3a.setName("Bag of Fish Food");
        product3a.setDescription(description);
        product3a.setPrice(BigDecimal.valueOf(5.00));
        product3a.setImage("https://i.ibb.co/vD02q8S/food1.png");
        product3a.setCategory(category3);
        productRepository.save(product3a);

        Product product3b = new Product();
        product3b.setName("Box of Fish Burgers");
        product3b.setDescription(description);
        product3b.setPrice(BigDecimal.valueOf(5.00));
        product3b.setImage("https://i.ibb.co/KwmJZr2/food2.png");
        product3b.setCategory(category3);
        productRepository.save(product3b);

        // Other sea creatures
        Product product4a = new Product();
        product4a.setName("Party Crocodile");
        product4a.setDescription(description);
        product4a.setPrice(BigDecimal.valueOf(150.00));
        product4a.setImage("https://i.ibb.co/KhtyWJY/croc.png");
        product4a.setCategory(category4);
        productRepository.save(product4a);

        Product product4b = new Product();
        product4b.setName("Funny Dolphin");
        product4b.setDescription(description);
        product4b.setPrice(BigDecimal.valueOf(150.00));
        product4b.setImage("https://i.ibb.co/0njcQNL/dolphin.png");
        product4b.setCategory(category4);
        productRepository.save(product4b);

        Product product4c = new Product();
        product4c.setName("Rock Lobster");
        product4c.setDescription(description);
        product4c.setPrice(BigDecimal.valueOf(43.21));
        product4c.setImage("https://i.ibb.co/VS2qyWc/lobster.png");
        product4c.setCategory(category4);
        productRepository.save(product4c);

        Product product4d = new Product();
        product4d.setName("Dr Octopus");
        product4d.setDescription(description);
        product4d.setPrice(BigDecimal.valueOf(88.00));
        product4d.setImage("https://i.ibb.co/8MbmzG5/octo.png");
        product4d.setCategory(category4);
        productRepository.save(product4d);

        Product product4e = new Product();
        product4e.setName("Hungry Piranha");
        product4e.setDescription(description);
        product4e.setPrice(BigDecimal.valueOf(35.50));
        product4e.setImage("https://i.ibb.co/g3FZ03x/piranha.png");
        product4e.setCategory(category4);
        productRepository.save(product4e);

        Product product4f = new Product();
        product4f.setName("Feisty Seahorse");
        product4f.setDescription(description);
        product4f.setPrice(BigDecimal.valueOf(74.99));
        product4f.setImage("https://i.ibb.co/zHccp0x/seahorse.png");
        product4f.setCategory(category4);
        productRepository.save(product4f);

        Product product4g = new Product();
        product4g.setName("Sneaky Shark");
        product4g.setDescription(description);
        product4g.setPrice(BigDecimal.valueOf(1075.00));
        product4g.setImage("https://i.ibb.co/LkPQ2SJ/shark.png");
        product4g.setCategory(category4);
        productRepository.save(product4g);

        Product product4h = new Product();
        product4h.setName("S-S-S-Snake");
        product4h.setDescription(description);
        product4h.setPrice(BigDecimal.valueOf(199.00));
        product4h.setImage("https://i.ibb.co/xGTpzn2/snake.png");
        product4h.setCategory(category4);
        productRepository.save(product4h);

        Product product4i = new Product();
        product4i.setName("Stone Age Mutant Ninja Turtle");
        product4i.setDescription(description);
        product4i.setPrice(BigDecimal.valueOf(225.00));
        product4i.setImage("https://i.ibb.co/fdqb3bY/turtle.png");
        product4i.setCategory(category4);
        productRepository.save(product4i);















    }

}
