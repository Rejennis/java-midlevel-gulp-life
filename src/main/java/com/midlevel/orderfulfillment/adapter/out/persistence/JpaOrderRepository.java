package com.midlevel.orderfulfillment.adapter.out.persistence;

import com.midlevel.orderfulfillment.adapter.out.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.midlevel.orderfulfillment.domain.model.OrderStatus;
import java.util.List;

/**
 * Spring Data JPA Repository for OrderEntity.
 * 
 * Spring Data JPA auto-implements this interface at runtime.
 * No need to write implementation code!
 * 
 * JpaRepository<OrderEntity, String> means:
 * - Entity type: OrderEntity
 * - ID type: String
 * 
 * Provides built-in methods:
 * - save(), findById(), findAll(), deleteById(), etc.
 * 
 * Can define custom queries using:
 * - Method name conventions (findByCustomerId)
 * - @Query annotation for complex queries
 */
@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, String> {
    
    /**
     * Find orders by customer ID.
     * Method name convention: findBy + PropertyName
     * Spring Data auto-generates: SELECT * FROM orders WHERE customer_id = ?
     */
    List<OrderEntity> findByCustomerId(String customerId);
    
    /**
     * Find orders by status.
     * Spring Data auto-generates: SELECT * FROM orders WHERE status = ?
     */
    List<OrderEntity> findByStatus(OrderStatus status);
    
    /**
     * Find orders by customer and status.
     * Demonstrates combining multiple conditions.
     */
    List<OrderEntity> findByCustomerIdAndStatus(String customerId, OrderStatus status);
    
    /**
     * Custom query using @Query annotation.
     * Useful for complex queries that can't be expressed with method names.
     * 
     * JPQL (Java Persistence Query Language) - queries against entities, not tables
     */
    @Query("SELECT o FROM OrderEntity o WHERE o.customerId = :customerId " +
           "ORDER BY o.createdAt DESC")
    List<OrderEntity> findRecentOrdersByCustomer(@Param("customerId") String customerId);
    
    /**
     * Count orders by status.
     * Demonstrates derived count query.
     */
    long countByStatus(OrderStatus status);
    
    /**
     * Check if orders exist for a customer.
     * Returns true if at least one order exists.
     */
    boolean existsByCustomerId(String customerId);
}
