package be.ehb.petshopboys.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    // This auto-increments the id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    // This is a required field
    @NotBlank(message = "Category name is required.")

    // Validate the length of the category name (min = 1, max = 255)
    @Size(min = 1, max = 255, message = "Category name must be between 1 and 255 characters long.")
    private String name;

    // Validate the length of the description (max = 1000)
    @Column(length = 1000)
    @Size(max = 1000, message = "Description must not exceed 1000 characters.")
    private String description;

    // This is a one-to-many relationship. A category can have multiple products, but a product can only have one category.
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();

    public Category() {
        // Default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    // Standard equals, hashCode and toString method overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return id != null && id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Category{" + "id=" + id + ", name='" + name + '\'' + ", description='" + description + '\'' + ", productsCount=" + products.size() + '}';
    }
}
