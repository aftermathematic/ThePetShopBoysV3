package be.ehb.petshopboys.config;

import be.ehb.petshopboys.model.Category;
import be.ehb.petshopboys.model.Product;
import be.ehb.petshopboys.model.User;
import be.ehb.petshopboys.repository.CategoryRepository;
import be.ehb.petshopboys.repository.ProductRepository;
import be.ehb.petshopboys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Override
    public void run(String... args) throws Exception {
        clearDatabase();
        //loadUserData();
        loadCategoryData();
        loadProductData();

    }

    private void clearDatabase() {
        productRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }

    private void loadUserData() {

        User user1 = new User();
        user1.setEmail("admin@petshopboys.be");
        user1.setPassword(passwordEncoder.encode("123456789"));
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("jan.vermeerbergen@student.ehb.be");
        user2.setPassword(passwordEncoder.encode("123456789"));
        userRepository.save(user2);

    }


    private void loadCategoryData() {
        Category category1 = new Category();
        category1.setId(1L);
        category1.setName("Dogs");
        category1.setDescription("Category for dogs");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Cats");
        category2.setDescription("Category for cats");
        categoryRepository.save(category2);

        Category category3 = new Category();
        category3.setId(3L);
        category3.setName("Birds");
        category3.setDescription("Category for birds");
        categoryRepository.save(category3);

        Category category4 = new Category();
        category4.setId(4L);
        category4.setName("Fish");
        category4.setDescription("Category for fish");
        categoryRepository.save(category4);

        Category category5 = new Category();
        category5.setId(5L);
        category5.setName("Reptiles");
        category5.setDescription("Category for reptiles");
        categoryRepository.save(category5);
    }


    private void loadProductData() {

        Category category1 = categoryRepository.findByName("Dogs").orElseThrow();
        Category category2 = categoryRepository.findByName("Cats").orElseThrow();
        Category category3 = categoryRepository.findByName("Birds").orElseThrow();
        Category category4 = categoryRepository.findByName("Fish").orElseThrow();
        Category category5 = categoryRepository.findByName("Reptiles").orElseThrow();

        // Dummy description
        String description = "lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor " + "incididunt ut labore et dolore magna aliqua";

        Product product1a = new Product();
        product1a.setName("Labrador puppy");
        product1a.setDescription(description);
        product1a.setPrice(BigDecimal.valueOf(50.00));
        product1a.setCategory(category1);
        productRepository.save(product1a);

        Product product1b = new Product();
        product1b.setName("Poodle puppy");
        product1b.setDescription(description);
        product1b.setPrice(BigDecimal.valueOf(50.00));
        product1b.setCategory(category1);
        productRepository.save(product1b);

        Product product1c = new Product();
        product1c.setName("Dog food");
        product1c.setDescription(description);
        product1c.setPrice(BigDecimal.valueOf(10.00));
        product1c.setCategory(category1);
        productRepository.save(product1c);

        Product product2a = new Product();
        product2a.setName("Cat food");
        product2a.setDescription(description);
        product2a.setPrice(BigDecimal.valueOf(10.00));
        product2a.setCategory(category2);
        productRepository.save(product2a);

        Product product2b = new Product();
        product2b.setName("British Shorthair");
        product2b.setDescription(description);
        product2b.setPrice(BigDecimal.valueOf(50.00));
        product2b.setCategory(category2);
        productRepository.save(product2b);

        Product product2c = new Product();
        product2c.setName("Bengal");
        product2c.setDescription(description);
        product2c.setPrice(BigDecimal.valueOf(50.00));
        product2c.setCategory(category2);
        productRepository.save(product2c);

        Product product3a = new Product();
        product3a.setName("Parrot");
        product3a.setDescription(description);
        product3a.setPrice(BigDecimal.valueOf(50.00));
        product3a.setCategory(category3);
        productRepository.save(product3a);

        Product product3b = new Product();
        product3b.setName("Parakeet");
        product3b.setDescription(description);
        product3b.setPrice(BigDecimal.valueOf(50.00));
        product3b.setCategory(category3);
        productRepository.save(product3b);

        Product product3c = new Product();
        product3c.setName("Bird food");
        product3c.setDescription(description);
        product3c.setPrice(BigDecimal.valueOf(10.00));
        product3c.setCategory(category3);
        productRepository.save(product3c);

        Product product4a = new Product();
        product4a.setName("Goldfish");
        product4a.setDescription(description);
        product4a.setPrice(BigDecimal.valueOf(30.00));
        product4a.setCategory(category4);
        productRepository.save(product4a);

        Product product4b = new Product();
        product4b.setName("Tropical fish");
        product4b.setDescription(description);
        product4b.setPrice(BigDecimal.valueOf(30.00));
        product4b.setCategory(category4);
        productRepository.save(product4b);

        Product product4c = new Product();
        product4c.setName("Fish food");
        product4c.setDescription(description);
        product4c.setPrice(BigDecimal.valueOf(10.00));
        product4c.setCategory(category4);
        productRepository.save(product4c);

        Product product5a = new Product();
        product5a.setName("Turtle");
        product5a.setDescription(description);
        product5a.setPrice(BigDecimal.valueOf(50.00));
        product5a.setCategory(category5);
        productRepository.save(product5a);

        Product product5b = new Product();
        product5b.setName("Snake");
        product5b.setDescription(description);
        product5b.setPrice(BigDecimal.valueOf(50.00));
        product5b.setCategory(category5);
        productRepository.save(product5b);

        Product product5c = new Product();
        product5c.setName("Lizard");
        product5c.setDescription(description);
        product5c.setPrice(BigDecimal.valueOf(50.00));
        product5c.setCategory(category5);
        productRepository.save(product5c);

        Product product5d = new Product();
        product5d.setName("Reptile food");
        product5d.setDescription(description);
        product5d.setPrice(BigDecimal.valueOf(10.00));
        product5d.setCategory(category5);
        productRepository.save(product5d);

    }

}
