package be.ehb.petshopboys.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    // This auto-increments the id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // This is a many-to-one relationship. A product can be in multiple cart items, but a cart item can only have one product.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    // This is a required field
    @NotNull(message = "Product is required.")
    private Product product;

    @Column(nullable = false)
    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "User is required.")
    private User user;

    public CartItem() {
        // Default constructor
    }

    // This constructor is used when creating a new cart item
    public CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // This method calculates the total price of the cart item (price * quantity)
    public BigDecimal getTotalPrice() {
        if (product != null && product.getPrice() != null) {
            return product.getPrice().multiply(BigDecimal.valueOf(quantity));
        } else {
            return BigDecimal.ZERO;
        }
    }

    // Standard equals, hashCode and toString method overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;

        return Objects.equals(id, cartItem.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CartItem{" + "id=" + id + ", product=" + product + ", quantity=" + quantity + ", totalPrice=" + getTotalPrice() + '}';
    }
}