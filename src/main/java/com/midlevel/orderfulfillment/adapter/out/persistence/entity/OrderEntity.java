package com.midlevel.orderfulfillment.adapter.out.persistence.entity;

import com.midlevel.orderfulfillment.domain.model.*;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JPA Entity for Order - Adapter Layer
 * 
 * ARCHITECTURE PATTERN: Separate JPA entity from Domain model
 * 
 * Why separate entities from domain models?
 * 1. Keeps domain free from infrastructure concerns (Clean Architecture)
 * 2. JPA requires things domain shouldn't care about (@Id, @Column, etc.)
 * 3. Domain can evolve independently of database schema
 * 4. Easier to test domain logic without database
 * 5. Can map multiple domain models to one table or vice versa
 * 
 * This entity is in the "adapter" layer - it adapts between:
 * - Domain Model (Order.java) <-> Database (PostgreSQL table)
 */
@Entity
@Table(name = "orders")
public class OrderEntity {
    
    @Id
    @Column(name = "order_id", nullable = false, length = 50)
    private String orderId;
    
    @Column(name = "customer_id", nullable = false, length = 50)
    private String customerId;
    
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;
    
    @Column(name = "paid_at")
    private Instant paidAt;
    
    @Column(name = "shipped_at")
    private Instant shippedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;
    
    // Embedded value object for shipping address
    @Embedded
    private AddressEmbeddable shippingAddress;
    
    // One-to-Many relationship with OrderItems
    // CascadeType.ALL: Operations on Order cascade to OrderItems
    // orphanRemoval: If an item is removed from list, it's deleted from DB
    // FetchType.EAGER: Load items immediately with order (acceptable for small lists)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private List<OrderItemEntity> items = new ArrayList<>();
    
    /**
     * JPA requires a no-arg constructor.
     * Protected to prevent direct instantiation outside of JPA and this package.
     */
    protected OrderEntity() {
    }
    
    /**
     * Constructor for creating new OrderEntity from domain Order.
     * This is used when persisting a domain Order for the first time.
     */
    public OrderEntity(String orderId, String customerId, Instant createdAt,
                       OrderStatus status, AddressEmbeddable shippingAddress,
                       List<OrderItemEntity> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.createdAt = createdAt;
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.items = items;
    }
    
    /**
     * Converts this JPA entity to a domain Order.
     * This is the boundary between infrastructure and domain.
     * 
     * @return domain Order object
     */
    public Order toDomain() {
        // Convert items from entities to domain OrderItems
        List<OrderItem> domainItems = items.stream()
                .map(OrderItemEntity::toDomain)
                .collect(Collectors.toList());
        
        // Convert embedded address to domain Address
        Address domainAddress = shippingAddress.toDomain();
        
        // Create domain Order using factory method
        Order order = Order.create(customerId, domainItems, domainAddress);
        
        // Use reflection to set the internal state since Order doesn't expose setters
        // In a real application, you might add a package-private constructor or
        // reconstitution method in Order to avoid reflection
        try {
            java.lang.reflect.Field orderIdField = Order.class.getDeclaredField("orderId");
            orderIdField.setAccessible(true);
            orderIdField.set(order, this.orderId);
            
            java.lang.reflect.Field statusField = Order.class.getDeclaredField("status");
            statusField.setAccessible(true);
            statusField.set(order, this.status);
            
            java.lang.reflect.Field createdAtField = Order.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(order, this.createdAt);
            
            if (this.paidAt != null) {
                java.lang.reflect.Field paidAtField = Order.class.getDeclaredField("paidAt");
                paidAtField.setAccessible(true);
                paidAtField.set(order, this.paidAt);
            }
            
            if (this.shippedAt != null) {
                java.lang.reflect.Field shippedAtField = Order.class.getDeclaredField("shippedAt");
                shippedAtField.setAccessible(true);
                shippedAtField.set(order, this.shippedAt);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to reconstitute domain Order from entity", e);
        }
        
        return order;
    }
    
    /**
     * Creates a JPA entity from a domain Order.
     * This is used when persisting a domain Order.
     * 
     * @param order the domain Order
     * @return JPA OrderEntity
     */
    public static OrderEntity fromDomain(Order order) {
        // Convert domain OrderItems to entities
        List<OrderItemEntity> itemEntities = order.getItems().stream()
                .map(OrderItemEntity::fromDomain)
                .collect(Collectors.toList());
        
        // Convert domain Address to embeddable
        AddressEmbeddable addressEmbeddable = AddressEmbeddable.fromDomain(order.getShippingAddress());
        
        // Create entity
        OrderEntity entity = new OrderEntity(
                order.getOrderId(),
                order.getCustomerId(),
                order.getCreatedAt(),
                order.getStatus(),
                addressEmbeddable,
                itemEntities
        );
        
        entity.paidAt = order.getPaidAt();
        entity.shippedAt = order.getShippedAt();
        
        return entity;
    }
    
    // Getters and setters (required by JPA)
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    public Instant getPaidAt() {
        return paidAt;
    }
    
    public void setPaidAt(Instant paidAt) {
        this.paidAt = paidAt;
    }
    
    public Instant getShippedAt() {
        return shippedAt;
    }
    
    public void setShippedAt(Instant shippedAt) {
        this.shippedAt = shippedAt;
    }
    
    public OrderStatus getStatus() {
        return status;
    }
    
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    public AddressEmbeddable getShippingAddress() {
        return shippingAddress;
    }
    
    public void setShippingAddress(AddressEmbeddable shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public List<OrderItemEntity> getItems() {
        return items;
    }
    
    public void setItems(List<OrderItemEntity> items) {
        this.items = items;
    }
}
