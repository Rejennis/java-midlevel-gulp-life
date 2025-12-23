package com.midlevel.orderfulfillment.adapter.out.persistence.entity;

import com.midlevel.orderfulfillment.domain.model.Money;
import com.midlevel.orderfulfillment.domain.model.OrderItem;
import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * JPA Entity for OrderItem.
 * 
 * OrderItem is part of the Order aggregate but stored in a separate table
 * for normalization. However, it has no identity outside of its Order.
 */
@Entity
@Table(name = "order_items")
public class OrderItemEntity {
    
    // Surrogate key for JPA (not exposed in domain)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Foreign key to orders table (managed by JPA relationship in OrderEntity)
    @Column(name = "order_id", nullable = false, length = 50)
    private String orderId;
    
    @Column(name = "product_id", nullable = false, length = 50)
    private String productId;
    
    @Column(name = "product_name", nullable = false)
    private String productName;
    
    @Column(name = "unit_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;
    
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;
    
    @Column(name = "quantity", nullable = false)
    private int quantity;
    
    /**
     * JPA no-arg constructor.
     */
    protected OrderItemEntity() {
    }
    
    /**
     * Constructor for creating OrderItemEntity.
     */
    public OrderItemEntity(String productId, String productName,
                           BigDecimal unitPrice, String currency, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.currency = currency;
        this.quantity = quantity;
    }
    
    /**
     * Converts this entity to domain OrderItem.
     */
    public OrderItem toDomain() {
        Money price = Money.of(unitPrice, currency);
        return OrderItem.of(productId, productName, price, quantity);
    }
    
    /**
     * Creates entity from domain OrderItem.
     */
    public static OrderItemEntity fromDomain(OrderItem item) {
        return new OrderItemEntity(
                item.getProductId(),
                item.getProductName(),
                item.getUnitPrice().getAmount(),
                item.getUnitPrice().getCurrencyCode(),
                item.getQuantity()
        );
    }
    
    // Getters and setters
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
