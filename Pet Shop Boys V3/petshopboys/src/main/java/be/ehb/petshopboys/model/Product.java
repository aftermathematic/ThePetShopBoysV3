package be.ehb.petshopboys.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @NotBlank(message = "Product name is required.")
    @Size(min = 1, max = 255, message = "Product name must be between 1 and 255 characters long.")
    private String name;

    @Column(length = 1000)
    @Size(max = 1000, message = "Description must not exceed 1000 characters.")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Price is required.")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
    private BigDecimal price;

    @Column(nullable = false)
    @NotBlank(message = "Product image is required.")
    @Size(min = 1, max = 255, message = "Product image must be between 1 and 255 characters long.")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY) // Assuming Product:Category as Many:One mapping
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Category is required.")
    private Category category;

    public Product() {
        // Default constructor
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id != null && id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", price=" + price + ", categoryId=" + (category != null ? category.getId() : "null") + '}';
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
